package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.identification.CarBrandDto;
import com.hillel.evo.adviser.dto.identification.CarModelDto;
import com.hillel.evo.adviser.dto.identification.TypeCarDto;
import com.hillel.evo.adviser.entity.identification.CarBrand;
import com.hillel.evo.adviser.entity.identification.CarModel;
import com.hillel.evo.adviser.entity.identification.TypeCar;
import com.hillel.evo.adviser.mapper.CarBrandMapper;
import com.hillel.evo.adviser.mapper.CarModelMapper;
import com.hillel.evo.adviser.mapper.TypeCarMapper;
import com.hillel.evo.adviser.repository.CarIdentificationRepository;
import com.hillel.evo.adviser.service.CarIdentificationService;
import org.springframework.stereotype.Service;

@Service
public class CarIdentificationServiceImpl implements CarIdentificationService {

    private transient final CarIdentificationRepository carIdentificationRepository;
    private transient final CarModelMapper carModelMapper;
    private transient final CarBrandMapper carBrandMapper;
    private transient final TypeCarMapper typeCarMapper;

    public CarIdentificationServiceImpl(CarIdentificationRepository carIdentificationRepository, CarModelMapper carModelMapper, CarBrandMapper carBrandMapper, TypeCarMapper typeCarMapper) {
        this.carIdentificationRepository = carIdentificationRepository;
        this.carModelMapper = carModelMapper;
        this.carBrandMapper = carBrandMapper;
        this.typeCarMapper = typeCarMapper;
    }

    @Override
    public CarModelDto findCarModelByName(String name) {
        CarModel carModel = carIdentificationRepository.findCarModelByName(name);
        return carModelMapper.toDto(carModel);
    }

    @Override
    public CarBrandDto findCarBrandByName(String name) {
        CarBrand carBrand = carIdentificationRepository.findCarBrandByName(name);
        return carBrandMapper.toDto(carBrand);
    }

    @Override
    public TypeCarDto findTypeCarByName(String name) {
        TypeCar typeCar = carIdentificationRepository.findTypeCarByName(name);
        return typeCarMapper.toDto(typeCar);
    }
}
