package com.hillel.evo.adviser.business.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "business")
@EqualsAndHashCode(of = {"id"})
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Embedded
    private Location location;

    @Embedded
    private Contact contact;

    private String workingDays;
    private String workingHours;

    @ManyToOne
//    @JoinColumn(name = "business_type_id")
    private TypeBusiness typeBusiness;

//    @ManyToOne
//    @JoinColumn(name = "businessUser_id")
//    private BusinessUser businessUser;

    @ManyToMany
    private List<ServiceBusiness> serviceBusinesses;
}
