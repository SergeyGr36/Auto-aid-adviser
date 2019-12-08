package com.hillel.evo.adviser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class HistoryLocation {
    @Positive
    private Double latitude;
    @Positive
    private Double longitude;
}

