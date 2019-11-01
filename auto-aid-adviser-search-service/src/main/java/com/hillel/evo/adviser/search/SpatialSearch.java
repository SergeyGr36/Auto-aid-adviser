package com.hillel.evo.adviser.search;

import java.util.List;

public interface SpatialSearch<T> {

    List<T> search(Class<T> clazz, double radius, double latitude, double longitude);
}
