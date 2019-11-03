package com.hillel.evo.adviser.business.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
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

    @Enumerated
    @Embedded
    private Location localization;

    @Enumerated
    @Embedded
    private Contact contact;

    private String workingDays;
    private String workingHours;

    @ManyToOne
    @JoinColumn(name = "business_type_id", referencedColumnName = "id")
    private transient TypeBusiness businessType;

    @ManyToOne
    @JoinColumn(name = "businessUser_id")
    private BusinessUser businessUser;

    @ManyToMany
//    @ElementCollection(targetClass = ServiceBusiness.class)
    private transient List<ServiceBusiness> serviceBusinesses;
}
