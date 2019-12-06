package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@DiscriminatorValue("car_brand")
public class CarBrand extends CarIdentification {

    public CarBrand(Long id, String name) {
        this.setId(id);
        this.setName(name);
    }
}
