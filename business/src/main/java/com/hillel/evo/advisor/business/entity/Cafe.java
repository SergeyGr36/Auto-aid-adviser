package com.hillel.evo.advisor.business.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "businessType_id")
    private BusinessType businessType;
    @OneToMany(mappedBy = "cafe")
    private List<CuisinesType> cuisinesType;
    @OneToMany(mappedBy = "cafe")
    private List<ServicesForCafe> servicesForCafe;
}
