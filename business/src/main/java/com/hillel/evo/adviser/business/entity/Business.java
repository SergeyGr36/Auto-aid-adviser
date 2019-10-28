package com.hillel.evo.adviser.business.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Entity
@Table(name = "business_point")
@EqualsAndHashCode(of = {"id"})
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Positive
    private double longitude;

    @Positive
    private double latitude;

    private String description; //such as working days, hours, contact phone...

    @OneToOne
    @JoinColumn(name = "business_type_id", referencedColumnName = "id")
    private BusinessType businessType;

    @ManyToOne
    @JoinColumn(name = "businessUser_id")
    private BusinessUser businessUser;

}
