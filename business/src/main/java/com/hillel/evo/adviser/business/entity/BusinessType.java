package com.hillel.evo.adviser.business.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
public class BusinessType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String type;
    @OneToOne(mappedBy = "businessType")
    private Business business;
    @OneToMany(mappedBy = "businessType")
    private List<Sto> sto;
    @OneToMany(mappedBy = "businessType")
    private List<Cafe> cafe;
}
