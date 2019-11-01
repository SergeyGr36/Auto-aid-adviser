package com.hillel.evo.adviser.search;

import java.util.List;

public interface CustomSearch<T> {

    List<T> search(Class<T> clazz, QueryFactory... queries);
}
