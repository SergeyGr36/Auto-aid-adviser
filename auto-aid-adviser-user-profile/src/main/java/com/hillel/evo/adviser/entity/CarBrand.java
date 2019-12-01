package com.hillel.evo.adviser.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@DiscriminatorValue("CarBrand")
public class CarBrand extends CarIdentification {
    @Builder
    public  CarBrand(Long id, String name){
        super(id, name);
    }
}
