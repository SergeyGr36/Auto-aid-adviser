package com.hillel.evo.advisor.business.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class ServicesForCafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String type;
    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;
}
