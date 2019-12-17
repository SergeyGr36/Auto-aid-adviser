package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.identification.CarBrandDto;
import com.hillel.evo.adviser.dto.identification.CarModelDto;
import com.hillel.evo.adviser.entity.identification.CarBrand;
import com.hillel.evo.adviser.entity.identification.CarModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TypeCarMapper.class})
public interface CarBrandMapper {

    @Mapping(target = "typeCar", source = "parent")
    CarBrandDto toDto(CarBrand entity);
    @Mapping(target = "parent", source = "typeCar")
    CarBrand toEntity(CarBrandDto dto);
}
