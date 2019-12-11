package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.CarBrandDto;
import com.hillel.evo.adviser.dto.FuelTypeDto;
import com.hillel.evo.adviser.dto.MotorTypeDto;
import com.hillel.evo.adviser.dto.TypeCarDto;
import com.hillel.evo.adviser.entity.CarBrand;
import com.hillel.evo.adviser.entity.FuelType;
import com.hillel.evo.adviser.entity.MotorType;
import com.hillel.evo.adviser.entity.TypeCar;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarIdentificationMapper {

    CarBrandDto toDto(CarBrand entity);
    CarBrand toEntity(CarBrandDto dto);

    MotorTypeDto toDto(MotorType entity);
    MotorType toEntity(MotorTypeDto dto);

    TypeCarDto toDto(TypeCar entity);
    TypeCar toEntity(TypeCarDto dto);

    FuelTypeDto toDto(FuelType entity);
    FuelType toEntity(FuelTypeDto dto);
}
