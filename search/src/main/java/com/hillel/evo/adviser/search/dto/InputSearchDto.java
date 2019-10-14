package com.hillel.evo.adviser.search.dto;

import lombok.Data;

import java.util.List;

@Data
public class InputSearchDto {

    private List<String> aidTypes;
    private double curLat;
    private double curLong;
    private double minLat;
    private double minLong;
    private double maxLat;
    private double maxLong;
}
