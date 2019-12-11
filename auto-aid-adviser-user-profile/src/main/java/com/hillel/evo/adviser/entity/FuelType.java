package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@DiscriminatorValue("fuel_type")
@NoArgsConstructor
public class FuelType extends CarIdentification{
    public FuelType(Long id, String name) {
        this.setId(id);
        this.setName(name);
    }
}
