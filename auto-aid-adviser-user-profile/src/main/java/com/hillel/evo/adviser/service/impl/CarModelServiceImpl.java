package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.CarModelDto;
import com.hillel.evo.adviser.entity.CarModel;
import com.hillel.evo.adviser.mapper.CarModelMapper;
import com.hillel.evo.adviser.repository.CarModelRepository;
import com.hillel.evo.adviser.service.CarModelService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class CarModelServiceImpl implements CarModelService {

    private final CarModelRepository carModelRepository;
    private final CarModelMapper carModelMapper;

    public CarModelServiceImpl(CarModelRepository carModelRepository, CarModelMapper carModelMapper) {
        this.carModelRepository = carModelRepository;
        this.carModelMapper = carModelMapper;
    }

    @Override
    public CarModelDto findById(@NotNull Long id) {
        Optional<CarModel> carModel = carModelRepository.findById(id);
        return carModel.map(m -> carModelMapper.toDto(m))
                .orElseThrow(() -> new RuntimeException("Model not found by id: " + id));
    }

    @Override
    public CarModelDto findByName(@NotNull String name) {
        Optional<CarModel> carModel = carModelRepository.findByName(name);
        return carModel.map(m -> carModelMapper.toDto(m))
                .orElseThrow(() -> new RuntimeException("Model not found by name: " + name));
    }

    @Override
    public List<CarModelDto> findByTypeAndBrand(@NotNull String typeName, @NotNull String brandName) {
        List<CarModel> models = carModelRepository.findByTypeAndBrand(typeName, brandName);
        return carModelMapper.toListDto(models);
    }

    @Override
    public List<CarModelDto> findByTypeAndBrand(long typeId, long brandId) {
        List<CarModel> models = carModelRepository.findByTypeAndBrand(typeId, brandId);
        return carModelMapper.toListDto(models);
    }
}
