package com.hillel.evo.advisor.business.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Sto {
    @Id
    private long id;
    @OneToMany(mappedBy = "sto")
    private List<ServicesForSTO> servicesForSTO;
    @OneToMany(mappedBy = "sto")
    private List<MotorType> motorTypes;
    @OneToMany(mappedBy = "sto")
    private List<CarBrand> carBrands;
    @OneToMany(mappedBy = "sto")
    private List<CarType> carTypes;
    @OneToMany(mappedBy = "sto")
    private List<Transmission> transmissions;
    @ManyToOne
    @JoinColumn(name = "businessType_id")
    private BusinessType businessType;
}
