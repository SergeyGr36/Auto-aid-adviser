package com.hillel.evo.adviser.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

@Entity(name = "user_car")
@Data
public class UserCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private SimpleUser simpleUser;
    @ManyToOne
    private CarBrand brand;
    @ManyToOne
    private MotorType motorType;
    @ManyToOne
    private  TypeCar typeCar;
}
