package com.hillel.evo.adviser.business.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "businessType_id")
    private BusinessType businessType;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<CuisinesType> cuisinesType;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<ServicesForCafe> servicesForCafe;
}
