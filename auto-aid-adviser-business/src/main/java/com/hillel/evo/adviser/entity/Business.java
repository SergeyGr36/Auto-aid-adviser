package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "business")
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    private BusinessUser businessUser;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "business_has_service")
    private List<ServiceForBusiness> serviceForBusinesses;
}
