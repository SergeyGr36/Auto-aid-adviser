package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.identification.TypeCarDto;
import com.hillel.evo.adviser.entity.identification.TypeCar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TypeCarMapper {

    TypeCarDto toDto(TypeCar entity);
    @Mapping(target = "parent", ignore = true)
    TypeCar toEntity(TypeCarDto dto);
}
