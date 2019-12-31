package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.CarBrandDto;

import java.util.List;

public interface CarBrandService {

    CarBrandDto findById(long id);
    CarBrandDto findByName(String name);
    List<CarBrandDto> findAll();
}
