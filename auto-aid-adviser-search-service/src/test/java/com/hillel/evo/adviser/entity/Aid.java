package com.hillel.evo.adviser.entity;

import lombok.Data;
import org.hibernate.search.annotations.*;

import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Indexed
@Spatial
@Table
public class Aid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Field(store = Store.YES)
    private String name;

    @Field(analyze = Analyze.NO)
    @Facet
    private String type;

    @Latitude
    @Field(analyze = Analyze.NO)
    @Facet
    private double latitude;

    @Longitude
    private double longitude;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
