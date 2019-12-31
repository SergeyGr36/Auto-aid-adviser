package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.TypeCarDto;

import java.util.List;

public interface TypeCarService {

    TypeCarDto findById(Long id);
    TypeCarDto findByName(String name);
    List<TypeCarDto> findAll();
}
