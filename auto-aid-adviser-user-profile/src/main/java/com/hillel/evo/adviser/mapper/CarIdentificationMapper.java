package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.CarIdentificationDto;
import com.hillel.evo.adviser.entity.CarIdentification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserCarMapper.class)
public interface CarIdentificationMapper {
    CarIdentification toEntity(CarIdentificationDto dto);
    CarIdentificationDto toDto(CarIdentification identification);
}
