package com.hillel.evo.adviser.business.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.Positive;

@Data
@Embeddable
public class Location {

    @Positive
    private double longitude;

    @Positive
    private double latitude;

    private String address;
}
