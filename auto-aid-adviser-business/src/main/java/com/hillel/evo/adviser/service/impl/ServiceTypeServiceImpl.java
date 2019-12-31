package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.ServiceTypeDto;
import com.hillel.evo.adviser.entity.ServiceType;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.ServiceTypeMapper;
import com.hillel.evo.adviser.repository.ServiceTypeRepository;
import com.hillel.evo.adviser.search.CustomSearch;
import com.hillel.evo.adviser.search.TextSearch;
import com.hillel.evo.adviser.service.QueryGeneratorService;
import com.hillel.evo.adviser.service.ServiceTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

    private final static String QUERY_FIELD = "name";

    private transient final ServiceTypeMapper mapper;
    private transient final ServiceTypeRepository repository;
    private transient final TextSearch<ServiceType> textSearch;
    private transient final CustomSearch<ServiceType> customSearch;
    private transient final QueryGeneratorService queryGeneratorService;

    public ServiceTypeServiceImpl(ServiceTypeMapper mapper, ServiceTypeRepository repository, TextSearch<ServiceType> textSearch, CustomSearch<ServiceType> customSearch, QueryGeneratorService queryGeneratorService) {
        this.mapper = mapper;
        this.repository = repository;
        this.textSearch = textSearch;
        this.customSearch = customSearch;
        this.queryGeneratorService = queryGeneratorService;
    }

    @Override
    @Transactional
    public ServiceTypeDto createServiceType(ServiceTypeDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public List<ServiceTypeDto> findAllByBusinessTypeId(Long id) {
        return mapper.toDto(repository.findAllByBusinessTypeId(id));
    }

    @Override
    @Transactional
    public ServiceTypeDto findByName(String name) {
        var result = textSearch.search(ServiceType.class, QUERY_FIELD, name);
        //return mapper.toDto(textSearch.search(ServiceType.class, "name", name).get(0));
        return result.size() > 0 ? mapper.toDto(result.get(0)) : null;
    }

    @Override
    @Transactional
    public List<ServiceTypeDto> findAllByName(String name) {
        var clazz = ServiceType.class;
        var sQuery = queryGeneratorService.getTextWildcardQuery(clazz, QUERY_FIELD, name);
        return mapper.toDto(customSearch.search(clazz, sQuery));
    }

    @Override
    @Transactional
    public List<ServiceTypeDto> findAllByName(String name, String btName) {
        var clazz = ServiceType.class;
        var btQuery = queryGeneratorService.getTextQuery(clazz, "businessType.name", btName);
        var sQuery = queryGeneratorService.getTextWildcardQuery(clazz, QUERY_FIELD, name);
        return mapper.toDto(customSearch.search(clazz, btQuery, sQuery));
    }

    @Override
    public ServiceTypeDto getServiceTypeById(Long id) {
        return mapper.toDto(repository.findByIdEagerFetch(id).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    @Transactional
    public ServiceTypeDto updateServiceType(ServiceTypeDto dto) {
        return createServiceType(dto);
    }

    @Override
    public void deleteServiceType(Long id) {
        try {
            repository.deleteById(id);
        } catch (RuntimeException e){
            throw new DeleteException("Delete failed");
        }
    }

    @Override
    public Page<ServiceTypeDto> findAllByPages(Integer page, Integer size) {
        Page<ServiceType> pageEntity = repository.findAllByPages(PageRequest.of(page, size, Sort.by(QUERY_FIELD)));
        return pageEntity.map(mapper::toDto);
    }
}
