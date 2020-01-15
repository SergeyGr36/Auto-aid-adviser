package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchSpatialDTO extends SearchInputDTO {

    private Class<?> clazz;
    private double radius;
    private double latitude;
    private double longitude;
}
