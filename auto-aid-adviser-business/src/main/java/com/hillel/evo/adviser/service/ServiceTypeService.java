package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.ServiceTypeDto;

import java.util.List;

public interface ServiceTypeService {
    ServiceTypeDto createServiceType(ServiceTypeDto dto);
    List<ServiceTypeDto> findAllByBusinessTypeId(Long id);
    List<ServiceTypeDto> findAllByName(String name);
    List<ServiceTypeDto> findAllByNameContains(String name, String btName);
    ServiceTypeDto getServiceTypeById(Long id);
    ServiceTypeDto updateServiceType(ServiceTypeDto dto);
    void deleteServiceType(Long id);
    List<ServiceTypeDto> findAll();
}
