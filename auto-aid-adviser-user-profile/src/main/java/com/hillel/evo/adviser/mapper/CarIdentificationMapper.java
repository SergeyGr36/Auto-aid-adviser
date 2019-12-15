package com.hillel.evo.adviser.mapper;

import com.hillel.evo.adviser.dto.identification.CarBrandDto;
import com.hillel.evo.adviser.dto.identification.FuelTypeDto;
import com.hillel.evo.adviser.dto.identification.MotorTypeDto;
import com.hillel.evo.adviser.dto.identification.TypeCarDto;
import com.hillel.evo.adviser.entity.identification.CarBrand;
import com.hillel.evo.adviser.entity.identification.FuelType;
import com.hillel.evo.adviser.entity.identification.MotorType;
import com.hillel.evo.adviser.entity.identification.TypeCar;
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
