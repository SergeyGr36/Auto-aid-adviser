package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryLocationDto {
    @Positive
    private Double latitude;
    @Positive
    private Double longitude;
}