package com.hillel.evo.advisor.business.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Entity
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    @Positive
    private double longitude;
    @Positive
    private double latitude;
    private String contactPhone;
//    на рахунок формату уточнить. Можливо timestamp
    private String workingDays;
    private String workingHours;
    @OneToOne
    @JoinColumn(name = "business_type_id", referencedColumnName = "id")
    private BusinessType businessType;
    @ManyToOne
    @JoinColumn(name = "businessUser_id")
    private BusinessUser businessUser;

}
