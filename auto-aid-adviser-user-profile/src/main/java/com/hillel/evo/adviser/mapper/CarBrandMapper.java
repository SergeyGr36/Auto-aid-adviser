package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.CarBrandDto;
import com.hillel.evo.adviser.entity.CarBrand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarBrandMapper {

    CarBrand toBrand(CarBrandDto dto);
    CarBrandDto toDto(CarBrand brand);

}
