package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.TypeCarDto;
import com.hillel.evo.adviser.entity.TypeCar;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TypeCarMapper {

    TypeCarDto toDto(TypeCar entity);
    TypeCar toEntity(TypeCarDto dto);
    List<TypeCar> toListEntity(List<TypeCarDto> list);
    List<TypeCarDto> toListDto(List<TypeCar> list);
}
