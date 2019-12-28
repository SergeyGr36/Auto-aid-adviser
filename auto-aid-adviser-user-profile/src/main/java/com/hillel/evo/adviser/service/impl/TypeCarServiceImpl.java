package com.hillel.evo.adviser.service.impl;

import com.hillel.evo.adviser.dto.TypeCarDto;
import com.hillel.evo.adviser.entity.TypeCar;
import com.hillel.evo.adviser.exception.ResourceNotFoundException;
import com.hillel.evo.adviser.mapper.TypeCarMapper;
import com.hillel.evo.adviser.repository.TypeCarRepository;
import com.hillel.evo.adviser.service.TypeCarService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class TypeCarServiceImpl implements TypeCarService {

    private transient final TypeCarRepository typeCarRepository;
    private transient final TypeCarMapper typeCarMapper;

    public TypeCarServiceImpl(TypeCarRepository typeCarRepository, TypeCarMapper typeCarMapper) {
        this.typeCarRepository = typeCarRepository;
        this.typeCarMapper = typeCarMapper;
    }

    @Override
    public TypeCarDto findById(@NotNull Long id) {
        Optional<TypeCar> typeCar = typeCarRepository.findById(id);
        return typeCar.map(t -> typeCarMapper.toDto(t))
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle type not found by identifier: " + id));
    }

    @Override
    public TypeCarDto findByName(@NotNull String name) {
        Optional<TypeCar> typeCar = typeCarRepository.findByName(name);
        return typeCar.map(t -> typeCarMapper.toDto(t))
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle type not found by name: " + name));
    }

    @Override
    public List<TypeCarDto> findAll() {
        List<TypeCar> all = typeCarRepository.findAll();
        return typeCarMapper.toListDto(all);
    }
}
