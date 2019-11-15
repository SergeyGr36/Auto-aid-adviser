package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.ServiceForBusinessDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ServiceForBusinessService {
    ServiceForBusinessDto getServiceBusinessById(Long id);
    List<ServiceForBusinessDto> getAllByServiceTypeId(Long id);
    ServiceForBusinessDto createServiceBusiness(ServiceForBusinessDto dto);
    ServiceForBusinessDto updateServiceBusiness(ServiceForBusinessDto dto);
    void deleteServiceBusiness(Long id);
    Page<ServiceForBusinessDto> byPages(Integer page, Integer size);

}
