package com.hillel.evo.adviser.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.search.spatial.Coordinates;

import javax.persistence.Embeddable;
import javax.validation.constraints.Positive;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Location implements Coordinates {

    @Positive
    private Double longitude;

    @Positive
    private Double latitude;

    private String address;

    @Override
    public Double getLatitude() {
        return latitude;
    }

    @Override
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
