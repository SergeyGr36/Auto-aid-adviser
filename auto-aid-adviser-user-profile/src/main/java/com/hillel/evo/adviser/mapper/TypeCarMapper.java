package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.TypeCarDto;
import com.hillel.evo.adviser.entity.TypeCar;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeCarMapper {
    TypeCar toEntity(TypeCarDto dto);
    TypeCarDto toDto(TypeCar car);
}

