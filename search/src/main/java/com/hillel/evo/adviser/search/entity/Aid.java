package com.hillel.evo.adviser.search.entity;

import lombok.Data;
import org.hibernate.search.annotations.*;

import javax.persistence.*;

@Entity
@Indexed
@Data
@Spatial
@Table(name = "aid")
public class Aid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Field
    private String name;

    @Field(store = Store.YES)
    private String type;

    @Latitude
    private double latitude;

    @Longitude
    private double longitude;

}
