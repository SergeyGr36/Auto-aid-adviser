package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import com.hillel.evo.adviser.entity.ServiceForBusiness;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.mapper.ServiceForBusinessMapper;
import com.hillel.evo.adviser.repository.ServiceForBusinessRepository;
import com.hillel.evo.adviser.service.ServiceForBusinessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class ServiceForBusinessServiceImpl implements ServiceForBusinessService {
    private transient final ServiceForBusinessMapper mapper;
    private transient final ServiceForBusinessRepository repository;

    public ServiceForBusinessServiceImpl(ServiceForBusinessMapper mapper, ServiceForBusinessRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    @Transactional
    public ServiceForBusinessDto getServiceBusinessById(Long id) {
        return mapper.toDto(repository.getOne(id));
    }

    @Override
    @Transactional
    public List<ServiceForBusinessDto> getAllByServiceTypeId(Long id) {
        return mapper.toDto(repository.findAllByServiceType_Id(id));
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
        Page<ServiceForBusiness> pageServices = repository.byPages(PageRequest.of(page, size, new Sort(Sort.Direction.ASC, "name")));
        Page<ServiceForBusinessDto> listDto = pageServices.map(mapper::toDto);
        return listDto;
    }
}
