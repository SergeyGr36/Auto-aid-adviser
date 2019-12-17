package com.hillel.evo.adviser.entity;

import com.hillel.evo.adviser.entity.identification.CarModel;
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
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_car_id")
    Set<Image> images = new HashSet<>();
}
