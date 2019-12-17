package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.identification.CarBrandDto;
import com.hillel.evo.adviser.entity.identification.CarBrand;
import org.mapstruct.Mapping;

import java.util.List;

public interface CarBrandMapper {

    CarBrandDto toDto(CarBrand entity);
    CarBrand toEntity(CarBrandDto dto);
    List<CarBrand> toListEntity(List<CarBrandDto> dtos);
    List<CarBrandDto> toListDto(List<CarBrand> brands);
}
