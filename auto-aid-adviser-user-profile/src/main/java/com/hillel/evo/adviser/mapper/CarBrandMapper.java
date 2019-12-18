package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.CarBrandDto;
import com.hillel.evo.adviser.entity.CarBrand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarBrandMapper {

    CarBrandDto toDto(CarBrand entity);
    CarBrand toEntity(CarBrandDto dto);
    List<CarBrand> toListEntity(List<CarBrandDto> dtos);
    List<CarBrandDto> toListDto(List<CarBrand> brands);
}
