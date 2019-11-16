package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.dto.ServiceForBusinessDto;

import java.util.List;

public interface BusinessService {
    BusinessDto createBusiness(BusinessDto dto, Long userId);
    List<BusinessDto> findAllByUser(Long userId);
    BusinessDto findBusinessById(Long id, Long userId);
    BusinessDto updateBusiness(BusinessDto dto, Long userId);
    void deleteBusiness(Long id, Long userId);
    List<ServiceForBusinessDto> findServicesByBusinessId(Long businessId, Long userId);
}
