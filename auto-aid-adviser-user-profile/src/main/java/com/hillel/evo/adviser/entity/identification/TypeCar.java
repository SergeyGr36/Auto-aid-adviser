package com.hillel.evo.adviser.entity.identification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@DiscriminatorValue("type_car")
@NoArgsConstructor
public class TypeCar extends CarIdentification {

    public TypeCar(Long id, String name) {
        this.setId(id);
        this.setName(name);
    }

    public TypeCar(String name) {
        this.setName(name);
    }
}
