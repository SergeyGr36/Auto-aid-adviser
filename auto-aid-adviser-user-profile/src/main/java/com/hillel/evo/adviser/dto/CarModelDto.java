package com.hillel.evo.adviser.dto;

import com.hillel.evo.adviser.entity.TypeCar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarModelDto {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private CarBrandDto carBrand;

    @NotNull
    private TypeCarDto typeCar;
}
