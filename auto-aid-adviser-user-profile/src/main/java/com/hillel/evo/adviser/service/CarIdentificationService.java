package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.identification.CarBrandDto;
import com.hillel.evo.adviser.dto.identification.CarModelDto;
import com.hillel.evo.adviser.dto.identification.TypeCarDto;

public interface CarIdentificationService {

    CarModelDto findCarModelByName(String name);
    CarBrandDto findCarBrandByName(String name);
    TypeCarDto findTypeCarByName(String name);
}
