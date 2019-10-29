package com.hillel.evo.adviser.search;

import java.util.AbstractMap;
import java.util.List;

public interface TextSearch<T> {

    List<T> search(Class<T> clazz, String field, String param);
//    List<T> searchAll(Class<T> clazz, List<AbstractMap.SimpleEntry<String, String>> pairs);
//    List<T> searchAny(Class<T> clazz, List<AbstractMap.SimpleEntry<String, String>> pairs);
}
