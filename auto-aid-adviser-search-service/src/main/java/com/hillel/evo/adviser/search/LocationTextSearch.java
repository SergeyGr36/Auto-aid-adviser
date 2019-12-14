package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.entity.Location;

import java.util.List;

public interface LocationTextSearch<T> {
    List<Location> searchLocation(Class<T> clazz, double radius, double longtitude, double latitude, String address);
}
