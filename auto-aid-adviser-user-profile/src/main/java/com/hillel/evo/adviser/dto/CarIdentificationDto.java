package com.hillel.evo.adviser.dto;

import com.hillel.evo.adviser.entity.UserCar;
import lombok.Data;


@Data
public class CarIdentificationDto {

    private Long id;
    private UserCar car;
    private String name;
}
