package com.hillel.evo.advisor.business.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class CarType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String type;
    @ManyToOne
    @JoinColumn(name = "sto_id")
    private Sto sto;
}
