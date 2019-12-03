package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@Entity(name = "car_identification")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_ident")
public class CarIdentification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserCar car;
    @NotNull
    private String name;
    public CarIdentification(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
