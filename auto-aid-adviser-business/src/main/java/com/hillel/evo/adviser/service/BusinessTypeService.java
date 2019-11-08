package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.BusinessTypeDto;

import java.util.List;

public interface BusinessTypeService {
    BusinessTypeDto createBusinessType(BusinessTypeDto dto);
    List<BusinessTypeDto> findAll();
    BusinessTypeDto findBusinessTypeById(Long id);
    BusinessTypeDto updateBusinessType(BusinessTypeDto dto);
    void deleteBusinessType(Long id);
}
