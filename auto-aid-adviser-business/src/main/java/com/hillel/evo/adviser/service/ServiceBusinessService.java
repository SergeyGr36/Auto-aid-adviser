package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.ServiceBusinessDto;
import com.hillel.evo.adviser.entity.ServiceBusiness;

import java.util.List;

public interface ServiceBusinessService {
    ServiceBusinessDto getServiceBusinessById(Long id);
    List<ServiceBusinessDto> getAllByServiceTypeId(Long id);
    ServiceBusinessDto createServiceBusiness(ServiceBusinessDto dto);
    ServiceBusinessDto updateServiceBusiness(ServiceBusinessDto dto);
    void deleteServiceBusiness(Long id);
    List<ServiceBusinessDto> findAll();

}
