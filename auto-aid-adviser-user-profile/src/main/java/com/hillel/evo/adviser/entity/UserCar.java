package com.hillel.evo.adviser.entity;

import com.hillel.evo.adviser.entity.identification.CarBrand;
import com.hillel.evo.adviser.entity.identification.FuelType;
import com.hillel.evo.adviser.entity.identification.MotorType;
import com.hillel.evo.adviser.entity.identification.TypeCar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity(name = "user_car")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class UserCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer releaseYear;
    private String individualCarNaming;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private SimpleUser simpleUser;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private CarBrand brand;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motor_type_id")
    private MotorType motorType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_type_id")
    private TypeCar typeCar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fuel_type_id")
    private FuelType fuelType;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_car_id")
    Set<Image> images = new HashSet<>();

//    public UserCar(SimpleUser simpleUser, CarBrand brand, MotorType motorType, TypeCar typeCar, FuelType fuelType) {
//        this.simpleUser = simpleUser;
//        this.brand = brand;
//        this.motorType = motorType;
//        this.typeCar = typeCar;
//        this.fuelType = fuelType;
//    }
}
