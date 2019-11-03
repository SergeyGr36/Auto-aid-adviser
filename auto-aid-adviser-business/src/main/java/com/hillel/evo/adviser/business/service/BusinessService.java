package com.hillel.evo.adviser.business.service;

import com.hillel.evo.adviser.business.dto.BusinessDto;

import java.util.List;

public interface BusinessService {
    BusinessDto createBusiness(BusinessDto dto);
    List<BusinessDto> findAllByUser(Long id);
}
