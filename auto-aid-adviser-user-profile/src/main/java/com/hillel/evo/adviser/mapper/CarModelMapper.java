package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.CarModelDto;
import com.hillel.evo.adviser.entity.CarBrand;
import com.hillel.evo.adviser.entity.CarModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TypeCarMapper.class, CarBrandMapper.class})
public interface CarModelMapper {

    CarModelDto toDto(CarModel entity);
    CarModel toEntity(CarModelDto dto);
    List<CarModelDto> toListDto(List<CarModel> entity);
    List<CarModel> toListEntity(List<CarModelDto> dto);

}
