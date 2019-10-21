package com.hillel.evo.advisor.business.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.util.List;

@Data
@Entity
public class Sto {
    private long id;
    private String name;
    private List<ServicesForSTO> servicesForSTO;
    private List<MotorType> motorTypes;
    private List<CarBrand> carBrands;
    private List<CarType> carTypes;
    private List<Transmission> transmissions;
    private double longitude;
    private double latitude;
    private String contacts;
    private String workingDays;
    private String workingHours;
}
