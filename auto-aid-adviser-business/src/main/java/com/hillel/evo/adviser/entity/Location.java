package com.hillel.evo.adviser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Positive;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Positive
    private double longitude;

    @Positive
    private double latitude;

    private String address;

}
