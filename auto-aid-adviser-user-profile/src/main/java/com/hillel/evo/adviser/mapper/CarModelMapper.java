package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.identification.CarModelDto;
import com.hillel.evo.adviser.entity.identification.CarModel;

import java.util.List;

public interface CarModelMapper {

    CarModelDto toDto(CarModel entity);
    CarModel toEntity(CarModelDto dto);
    List<CarModelDto> toListDto(List<CarModel> entity);
    List<CarModel> toListEntity(List<CarModelDto> dto);

}
