package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.CarIdentificationDto;
import com.hillel.evo.adviser.entity.CarIdentification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserCarMapper.class)
public interface CarIdentificationMapper {
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "name", source = "dto.name")
    CarIdentification toEntity(CarIdentificationDto dto);
    @Mapping(target = "id", source = "identification.id")
    @Mapping(target = "name", source = "identification.name")
    CarIdentificationDto toDto(CarIdentification identification);
}
