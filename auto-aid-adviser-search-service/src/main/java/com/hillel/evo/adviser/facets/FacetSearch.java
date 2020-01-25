package com.hillel.evo.adviser.facets;

import org.hibernate.search.query.facet.Facet;
import org.hibernate.search.query.facet.FacetingRequest;

import java.util.List;
import java.util.function.Supplier;

public interface FacetSearch<T> {
    List<Facet> search(Class<T> clazz, Supplier<FacetingRequest>... facetingRequests);
}
