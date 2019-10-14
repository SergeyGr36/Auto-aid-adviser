package com.hillel.evo.adviser.search.entity;

import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Indexed
@Data
@Table(name = "aid")
public class Aid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Field
    private String name;

    @Field(store = Store.YES)
    private String type;

    @Field(store = Store.YES)
    private double latitude;

    @Field(store = Store.YES)
    private double longitude;

}
