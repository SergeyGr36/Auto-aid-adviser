package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.search.annotations.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Indexed
@Table(name = "service_type")
@EqualsAndHashCode(of = {"id"})
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Field(store = Store.YES)
    @Column(unique = true)
    private String name;

    @IndexedEmbedded
    @ManyToOne//(fetch = FetchType.LAZY)
    private BusinessType businessType;
}
