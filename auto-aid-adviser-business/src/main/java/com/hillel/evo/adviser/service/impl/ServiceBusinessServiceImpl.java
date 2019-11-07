package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.ServiceBusinessDto;
import com.hillel.evo.adviser.exception.DeleteException;
import com.hillel.evo.adviser.mapper.ServiceBusinessMapper;
import com.hillel.evo.adviser.repository.ServiceBusinessRepository;
import com.hillel.evo.adviser.service.ServiceBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class ServiceBusinessServiceImpl implements ServiceBusinessService {
    @Autowired
    private ServiceBusinessMapper mapper;
    private final ServiceBusinessRepository repository;

    public ServiceBusinessServiceImpl(ServiceBusinessRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public ServiceBusinessDto getServiceBusinessById(Long id) {
        return mapper.toDto(repository.getOne(id));
    }

    @Override
    @Transactional
    public List<ServiceBusinessDto> getAllByServiceTypeId(Long id) {
        final Iterable<Long> idForSearch = Arrays.asList(id);
        return mapper.toDto(repository.findAllById(idForSearch));
    }

    @Override
    public ServiceBusinessDto createServiceBusiness(ServiceBusinessDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public ServiceBusinessDto updateServiceBusiness(ServiceBusinessDto dto) {
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
}
