package com.hillel.evo.adviser.search;

import java.util.List;

public interface TextSearch<T> {

    List<T> search(Class<T> clazz, String field, String param);
}
