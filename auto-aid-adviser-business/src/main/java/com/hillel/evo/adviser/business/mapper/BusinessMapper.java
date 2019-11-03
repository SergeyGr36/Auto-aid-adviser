package com.hillel.evo.adviser.business.mapper;

import com.hillel.evo.adviser.business.dto.BusinessDto;
import com.hillel.evo.adviser.business.entity.Business;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusinessMapper {

    BusinessMapper INSTANCE = Mappers.getMapper(BusinessMapper.class);

    BusinessDto toDto(Business business);
    Business toEntity(BusinessDto dto);
}
