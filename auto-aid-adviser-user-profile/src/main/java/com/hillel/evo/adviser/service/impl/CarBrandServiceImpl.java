package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.CarBrandDto;
import com.hillel.evo.adviser.entity.CarBrand;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.CarBrandMapper;
import com.hillel.evo.adviser.repository.CarBrandRepository;
import com.hillel.evo.adviser.service.CarBrandService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class CarBrandServiceImpl implements CarBrandService {

    private final CarBrandRepository carBrandRepository;
    private final CarBrandMapper carBrandMapper;

    public CarBrandServiceImpl(CarBrandRepository carBrandRepository, CarBrandMapper carBrandMapper) {
        this.carBrandRepository = carBrandRepository;
        this.carBrandMapper = carBrandMapper;
    }

    @Override
    public CarBrandDto findById(long id) {
        Optional<CarBrand> carBrand = carBrandRepository.findById(id);
        return carBrand.map(b -> carBrandMapper.toDto(b))
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found by id: " + id));
    }

    @Override
    public CarBrandDto findByName(@NotNull String name) {
        Optional<CarBrand> carBrand = carBrandRepository.findByName(name);
        return carBrand.map(b -> carBrandMapper.toDto(b))
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found by name: " + name));
    }

    @Override
    public List<CarBrandDto> findAll() {
        List<CarBrand> all = carBrandRepository.findAll();
        return carBrandMapper.toListDto(all);
    }
}
