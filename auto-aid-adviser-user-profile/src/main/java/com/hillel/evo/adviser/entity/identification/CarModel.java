package com.hillel.evo.adviser.entity.identification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@DiscriminatorValue("car_model")
@NoArgsConstructor
public class CarModel extends CarIdentification<CarBrand> {

    public CarModel(Long id, String name, CarBrand carBrand) {
        this.setId(id);
        this.setName(name);
        this.setParent(carBrand);
    }
}
