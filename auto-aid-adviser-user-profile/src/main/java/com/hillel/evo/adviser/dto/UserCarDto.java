package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCarDto {
    private Long id;
    @NotNull
    private CarBrandDto brand;
    @NotNull
    private MotorTypeDto motorType;
    @NotNull
    private TypeCarDto typeCar;

}
