package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchSpatialLocationDTO extends SearchInputDTO {

    private Class<?> clazz;
    private String field;
    private double radius;
    private double latitude;
    private double longitude;
}
