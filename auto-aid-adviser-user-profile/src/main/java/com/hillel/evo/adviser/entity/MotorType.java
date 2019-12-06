package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@DiscriminatorValue("motor_type")
@NoArgsConstructor
public class MotorType extends CarIdentification {

    public MotorType(Long id, String name) {
        this.setId(id);
        this.setName(name);
    }
}
