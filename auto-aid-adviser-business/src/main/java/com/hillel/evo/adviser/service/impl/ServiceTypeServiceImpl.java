package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.ServiceTypeDto;
import com.hillel.evo.adviser.entity.ServiceType;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.ServiceTypeMapper;
import com.hillel.evo.adviser.repository.ServiceTypeRepository;
import com.hillel.evo.adviser.search.CustomSearch;
import com.hillel.evo.adviser.search.TextSearch;
import com.hillel.evo.adviser.service.SearchHelperService;
import com.hillel.evo.adviser.service.ServiceTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {
    private transient final ServiceTypeMapper mapper;
    private transient final ServiceTypeRepository repository;
    private transient final TextSearch<ServiceType> textSearch;
    private transient final CustomSearch<ServiceType> customSearch;
    private transient final SearchHelperService searchHelperService;

    public ServiceTypeServiceImpl(ServiceTypeMapper mapper, ServiceTypeRepository repository, TextSearch<ServiceType> textSearch, CustomSearch<ServiceType> customSearch, SearchHelperService searchHelperService) {
        this.mapper = mapper;
        this.repository = repository;
        this.textSearch = textSearch;
        this.customSearch = customSearch;
        this.searchHelperService = searchHelperService;
    }

    @Override
    @Transactional
    public ServiceTypeDto createServiceType(ServiceTypeDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public List<ServiceTypeDto> findAllByBusinessTypeId(Long id) {
        return mapper.toDto(repository.findAllByBusinessType_Id(id));
    }

    @Override
    public List<ServiceTypeDto> findAllByName(String name) {
        return mapper.toDto(textSearch.search(ServiceType.class, "name", name));
    }

    @Override
    public List<ServiceTypeDto> findAllByNameContains(String name, String btName) {
        var clazz = ServiceType.class;
        var btQuery = searchHelperService.getTextQuery(clazz, "businessType.name", btName);
        var sQuery = searchHelperService.getTextWildcardQuery(clazz, "name", name);
        return mapper.toDto(customSearch.search(clazz, btQuery, sQuery));
    }

    @Override
    public ServiceTypeDto getServiceTypeById(Long id) {
        return mapper.toDto(repository.findById_Fetch(id).orElseThrow(ResourceNotFoundException::new));
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
    public List<ServiceTypeDto> findAll() {
        return mapper.toDto(repository.findAll());
    }
}