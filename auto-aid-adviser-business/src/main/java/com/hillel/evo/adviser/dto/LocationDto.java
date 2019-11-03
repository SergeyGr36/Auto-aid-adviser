package com.hillel.evo.adviser.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationDto {
    private double longitude;
    private double latitude;
    private String address;
}
