package com.hillel.evo.adviser.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity(name = "car")
@Data
public class UserCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile profile;
    @OneToOne
    private CarBrand brand;
    @OneToOne
    private MotorType motorType;
    @OneToOne
    private  TypeCar typeCar;
}
