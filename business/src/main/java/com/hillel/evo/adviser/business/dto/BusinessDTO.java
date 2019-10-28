package com.hillel.evo.adviser.business.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class BusinessDTO {
    @NotNull
    private String name;
    @Positive
    private double longitude;
    @Positive
    private double latitude;
    @NotNull
    private String description;
    private String businessType; //питання на рахунок типу(строка чи екземпляр класу)
}
