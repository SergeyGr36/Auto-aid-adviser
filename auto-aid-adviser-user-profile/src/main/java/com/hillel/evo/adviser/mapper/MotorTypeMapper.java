package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.MotorTypeDto;
import com.hillel.evo.adviser.entity.MotorType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MotorTypeMapper {
    MotorType toType(MotorTypeDto dto);
    MotorTypeDto toDto(MotorType type);
}
