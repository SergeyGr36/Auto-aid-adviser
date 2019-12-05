package com.hillel.evo.adviser.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@DiscriminatorValue("motor_type")
public class MotorType extends CarIdentification {
    @Builder
    public  MotorType(Long id, String name){
        super(id, name);
    }
}
