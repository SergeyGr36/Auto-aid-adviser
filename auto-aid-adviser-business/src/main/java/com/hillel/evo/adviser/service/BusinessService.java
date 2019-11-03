package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.BusinessDto;

import java.util.List;

public interface BusinessService {
    BusinessDto createBusiness(BusinessDto dto);
    List<BusinessDto> findAllByUser(Long id);
}
