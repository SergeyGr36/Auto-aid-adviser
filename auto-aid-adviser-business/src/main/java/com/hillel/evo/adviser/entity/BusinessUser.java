package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
public class BusinessUser  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @OneToMany(mappedBy = "businessUser")
//    @ElementCollection(targetClass = Business.class)
//    private List<Business> business;

}
