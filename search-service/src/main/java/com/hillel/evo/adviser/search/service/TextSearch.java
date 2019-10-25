package com.hillel.evo.adviser.search.service;

import java.util.AbstractMap;
import java.util.List;

public interface TextSearch<T> {

    List<T> search(Class<T> clazz, AbstractMap.SimpleEntry<String, String> pair);
    List<T> searchAll(Class<T> clazz, List<AbstractMap.SimpleEntry<String, String>> pairs);
    List<T> searchAny(Class<T> clazz, List<AbstractMap.SimpleEntry<String, String>> pairs);
}
