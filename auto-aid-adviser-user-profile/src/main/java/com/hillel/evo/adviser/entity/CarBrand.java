package com.hillel.evo.adviser.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@DiscriminatorValue("car_brand")
@NoArgsConstructor
public class CarBrand extends CarIdentification {
    @Builder
    public  CarBrand(Long id, String name){
        super(id, name);
    }
}
