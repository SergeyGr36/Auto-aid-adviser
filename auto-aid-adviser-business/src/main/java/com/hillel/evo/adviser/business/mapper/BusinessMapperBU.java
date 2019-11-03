package com.hillel.evo.adviser.business.mapper;

import com.hillel.evo.adviser.business.dto.BusinessDto;
import com.hillel.evo.adviser.business.entity.Business;
import org.springframework.beans.BeanUtils;

public class BusinessMapperBU {
    public BusinessDto toDto(Business business) {
        BusinessDto dto = new BusinessDto();
        BeanUtils.copyProperties(business, dto);
        return dto;
    }

    public Business toEntity(BusinessDto dto) {
        Business business = new Business();
        BeanUtils.copyProperties(dto, business);
        return business;
    }
}
