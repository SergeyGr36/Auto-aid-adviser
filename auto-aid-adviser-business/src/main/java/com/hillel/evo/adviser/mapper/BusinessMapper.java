package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.entity.Business;
import org.mapstruct.Mapper;

@Mapper
public interface BusinessMapper {

    BusinessDto toDto(Business business);
    Business toEntity(BusinessDto dto);
}
