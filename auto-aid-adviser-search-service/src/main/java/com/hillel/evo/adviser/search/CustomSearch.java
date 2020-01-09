package com.hillel.evo.adviser.search;

import org.apache.lucene.search.Query;

import java.util.List;
import java.util.function.Supplier;

public interface CustomSearch<T> {

    List<T> search(Class<T> clazz, Supplier<Query>... queries);
}
