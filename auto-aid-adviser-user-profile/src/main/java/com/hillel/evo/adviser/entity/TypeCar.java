package com.hillel.evo.adviser.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@DiscriminatorValue("TypeCar")
public class TypeCar extends CarIdentification{
    @Builder
    public  TypeCar(Long id, String name){
        super(id, name);
    }
}
