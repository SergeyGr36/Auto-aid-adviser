package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.identification.TypeCarDto;
import com.hillel.evo.adviser.entity.identification.TypeCar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

public interface TypeCarMapper {

    TypeCarDto toDto(TypeCar entity);
    TypeCar toEntity(TypeCarDto dto);
}
