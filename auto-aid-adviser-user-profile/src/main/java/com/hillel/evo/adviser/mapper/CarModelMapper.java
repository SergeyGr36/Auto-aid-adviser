package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.identification.CarBrandDto;
import com.hillel.evo.adviser.dto.identification.CarModelDto;
import com.hillel.evo.adviser.entity.identification.CarBrand;
import com.hillel.evo.adviser.entity.identification.CarModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CarBrandMapper.class})
public interface CarModelMapper {

    @Mapping(target = "carBrand", source = "parent")
    CarModelDto toDto(CarModel entity);
    @Mapping(target = "parent", source = "carBrand")
    CarModel toEntity(CarModelDto dto);

}
