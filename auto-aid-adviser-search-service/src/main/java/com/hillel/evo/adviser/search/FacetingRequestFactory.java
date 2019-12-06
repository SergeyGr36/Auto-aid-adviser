package com.hillel.evo.adviser.search;

import org.hibernate.search.query.facet.FacetingRequest;

@FunctionalInterface
public interface FacetingRequestFactory {
    FacetingRequest get();
}
