package com.hillel.evo.adviser.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MotorTypeDto {
    private Long id;
    @NotNull
    private String name;
}
