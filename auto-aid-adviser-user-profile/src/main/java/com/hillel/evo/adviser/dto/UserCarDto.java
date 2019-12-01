package com.hillel.evo.adviser.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserCarDto {
    private Long id;
    @NotNull
    private CarBrandDto brandDto;
    @NotNull
    private MotorTypeDto motorTypeDto;
    @NotNull
    private TypeCarDto typeCarDto;

}
