package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Spatial;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "business")
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@Indexed
@Spatial
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


   // @NotNull
    @Field
    private String name;

    @Field
   // @Positive
    @org.hibernate.search.annotations.Longitude
    private double Longitude;

    @Field
   // @Positive
    @org.hibernate.search.annotations.Latitude
    private double Latitude;

    @Embedded
    private Location location;

    @Embedded
    private Contact contact;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "business_id")
    private Set<WorkTime> workTimes;

    @ManyToOne(fetch = FetchType.LAZY)
    private BusinessUser businessUser;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "business_has_service")
    private Set<ServiceForBusiness> serviceForBusinesses;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    Set<Image> images = new HashSet<>();
}
