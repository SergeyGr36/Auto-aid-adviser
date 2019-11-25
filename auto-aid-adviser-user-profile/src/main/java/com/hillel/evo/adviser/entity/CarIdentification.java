package com.hillel.evo.adviser.entity;

import lombok.Data;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;




@Data

@Entity(name = "car_identification")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_ident")
public class CarIdentification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserCar car;
//    private CarBrand brand;
//    private MotorType motorType;
//    private TypeCar typeCar;
    private String name;
}
