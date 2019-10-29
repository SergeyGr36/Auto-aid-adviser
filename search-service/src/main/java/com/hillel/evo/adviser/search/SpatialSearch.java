package com.hillel.evo.adviser.search;

//import javafx.util.Pair;

import java.util.AbstractMap;
import java.util.List;

public interface SpatialSearch<T> {

    List<T> search(Class<T> clazz, double radius, double latitude, double longitude);
//    List<T> searchAll(Class<T> clazz, List<AbstractMap.SimpleEntry<String, String>> pairs, double radius, double latitude, double longitude);
//    List<T> searchAny(Class<T> clazz, List<AbstractMap.SimpleEntry<String, String>> pairs, double radius, double latitude, double longitude);
}
