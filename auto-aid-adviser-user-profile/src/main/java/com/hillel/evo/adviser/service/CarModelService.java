package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.CarModelDto;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CarModelService {

    CarModelDto findById(@NotNull Long id);
    CarModelDto findByName(@NotNull String name);
    List<CarModelDto> findByTypeAndBrand(@NotNull String typeName, @NotNull String brandName);
    List<CarModelDto> findByTypeAndBrand(long typeId, long brandId);
}
