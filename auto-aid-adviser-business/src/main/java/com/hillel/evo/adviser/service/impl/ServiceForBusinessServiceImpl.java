package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.SearchTextDTO;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.entity.ServiceForBusiness;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.mapper.ServiceForBusinessMapper;
import com.hillel.evo.adviser.repository.ServiceForBusinessRepository;
import com.hillel.evo.adviser.search.TextSearch;
import com.hillel.evo.adviser.service.ServiceForBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceForBusinessServiceImpl implements ServiceForBusinessService {
    private transient final ServiceForBusinessMapper mapper;
    private transient final ServiceForBusinessRepository repository;
    private transient final TextSearch<ServiceForBusinessDto> textSearch;

    @Override
    @Transactional
    public ServiceForBusinessDto getServiceBusinessById(Long id) {
        return mapper.toDto(repository.getOne(id));
    }

    @Override
    @Transactional
    public List<ServiceForBusinessDto> getAllByServiceTypeId(Long id) {
        return mapper.toDtoList(repository.findAllByServiceTypeId(id));
    }

    @Override
    public List<ServiceForBusinessDto> getAllByServiceName(String name) {
        var dto = new SearchTextDTO(ServiceForBusiness.class, "name", name);
        return textSearch.searchWildcard(mapper, dto);
    }

    @Override
    public ServiceForBusinessDto createServiceBusiness(ServiceForBusinessDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public ServiceForBusinessDto updateServiceBusiness(ServiceForBusinessDto dto) {
        return createServiceBusiness(dto);
    }

    @Override
    public void deleteServiceBusiness(Long id) {
        try {
            repository.deleteById(id);
        } catch (RuntimeException e){
            throw new DeleteException("Delete failed");
        }
    }

    @Override
    public Page<ServiceForBusinessDto> byPages(Integer page, Integer size) {
        Page<ServiceForBusiness> pageServices = repository.byPages(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")));
        return pageServices.map(mapper::toDto);
    }
}
