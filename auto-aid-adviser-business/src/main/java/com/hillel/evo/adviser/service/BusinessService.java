package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.search.QueryFactory;

import java.util.List;

public interface BusinessService {
    BusinessDto createBusiness(BusinessDto dto, Long userId);
    List<BusinessDto> findAllByUser(Long userId);
    BusinessDto findBusinessById(Long id, Long userId);
    BusinessDto updateBusiness(BusinessDto dto, Long userId);
    void deleteBusiness(Long id, Long userId);
QueryFactory findBusinessByServiceType(final Class clazz, final String field, final String value);
QueryFactory findBusinessByLocation(Class clazz, double radius, double latitude, double longitude);


}
