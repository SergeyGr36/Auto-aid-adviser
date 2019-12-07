package com.hillel.evo.adviser.facets;

import org.hibernate.search.query.facet.Facet;

import java.util.List;

public interface FacetSearch<T> {
    List<Facet> search(Class<T> clazz, FacetingRequestFactory... facetingRequests);
}
