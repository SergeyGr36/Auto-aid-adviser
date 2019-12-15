package com.hillel.evo.adviser.dto;

import com.hillel.evo.adviser.dto.identification.CarBrandDto;
import com.hillel.evo.adviser.dto.identification.FuelTypeDto;
import com.hillel.evo.adviser.dto.identification.MotorTypeDto;
import com.hillel.evo.adviser.dto.identification.TypeCarDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCarDto {
    private Long id;
    @Min(1910)
    @Max(2050)
    @Positive
    private Integer releaseYear;
    @Length(max = 30)
    private String individualCarNaming;
    @Length(max = 250)
    private String description;
    @NotNull
    private CarBrandDto brand;
    @NotNull
    private MotorTypeDto motorType;
    @NotNull
    private TypeCarDto typeCar;
    @NotNull
    private FuelTypeDto fuelType;
}
