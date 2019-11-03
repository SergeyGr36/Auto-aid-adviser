package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.BusinessDto;
import com.hillel.evo.adviser.entity.Business;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BusinessMapper {

    BusinessMapper INSTANCE = Mappers.getMapper(BusinessMapper.class);

    BusinessDto toDto(Business business);
    Business toEntity(BusinessDto dto);
}
