package com.hillel.evo.adviser.dto.identification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelTypeDto {
    private Long id;
    @NotNull
    private String name;
}
