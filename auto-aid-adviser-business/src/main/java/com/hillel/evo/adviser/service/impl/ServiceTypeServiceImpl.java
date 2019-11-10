package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.ServiceTypeDto;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.ServiceTypeMapper;
import com.hillel.evo.adviser.repository.ServiceTypeRepository;
import com.hillel.evo.adviser.service.ServiceTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {
    private transient final ServiceTypeMapper mapper;
    private transient final ServiceTypeRepository repository;

    public ServiceTypeServiceImpl(ServiceTypeMapper mapper, ServiceTypeRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
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
