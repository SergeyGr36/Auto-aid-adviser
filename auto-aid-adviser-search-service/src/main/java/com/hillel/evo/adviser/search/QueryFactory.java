package com.hillel.evo.adviser.search;

import org.apache.lucene.search.Query;

@FunctionalInterface
public interface QueryFactory {
    Query get();
}
