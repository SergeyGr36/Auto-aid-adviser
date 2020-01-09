package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.ServiceTypeDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ServiceTypeService {
    ServiceTypeDto createServiceType(ServiceTypeDto dto);
    List<ServiceTypeDto> findAllByBusinessTypeId(Long id);
    ServiceTypeDto findByName(String name);
    List<ServiceTypeDto> findAllByName(String name);
    List<ServiceTypeDto> findAllByName(String name, String btName);
    ServiceTypeDto getServiceTypeById(Long id);
    ServiceTypeDto updateServiceType(ServiceTypeDto dto);
    void deleteServiceType(Long id);
    Page<ServiceTypeDto> findAllByPages(Integer page, Integer size);
}
