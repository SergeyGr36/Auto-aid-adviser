package com.hillel.evo.adviser.business.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
public class BusinessUser {
//    для пошуку по id треба було ітерабл
    public Iterable<Long> getId;
    //    user details
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "businessUser")
    private List<Business> business;

}
