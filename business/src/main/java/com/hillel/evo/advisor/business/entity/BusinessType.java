package com.hillel.evo.advisor.business.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
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
