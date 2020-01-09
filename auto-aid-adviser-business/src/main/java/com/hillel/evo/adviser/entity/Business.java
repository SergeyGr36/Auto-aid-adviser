package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Spatial;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "business")
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@Indexed
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Field
    private String name;

    @Embedded
    @Spatial
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
    @IndexedEmbedded
    private Set<ServiceForBusiness> serviceForBusinesses;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    Set<Image> images = new HashSet<>();

}
