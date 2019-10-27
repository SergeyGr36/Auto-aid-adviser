package com.hillel.evo.advisor.business.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
public class BusinessUser {
    //    user details
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String type;
    @OneToMany(mappedBy = "businessUser")
    private List<Business> business;
}
