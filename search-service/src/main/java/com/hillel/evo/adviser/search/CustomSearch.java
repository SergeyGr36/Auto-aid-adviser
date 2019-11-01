package com.hillel.evo.adviser.search;

import org.apache.lucene.search.Query;

import java.util.AbstractMap;
import java.util.List;

public interface CustomSearch<T> {

    List<T> search(Class<T> clazz, QueryFactory... queries);
}
