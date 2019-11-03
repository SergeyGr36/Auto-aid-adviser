package com.hillel.evo.adviser.business.mapper;

import com.hillel.evo.adviser.business.dto.BusinessDto;
import com.hillel.evo.adviser.business.entity.Business;
import org.mapstruct.Mapper;

@Mapper
public interface BusinessMapper {

    BusinessDto toDto(Business business);
    Business toEntity(BusinessDto dto);
}
