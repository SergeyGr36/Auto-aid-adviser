package com.hillel.evo.adviser.business.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
public class Sto {

    @Id
    private long id;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<ServicesForSTO> servicesForSTO;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<MotorType> motorTypes;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<CarBrand> carBrands;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<CarType> carTypes;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Transmission> transmissions;

    @ManyToOne
    @JoinColumn(name = "businessType_id")
    private BusinessType businessType;
}
