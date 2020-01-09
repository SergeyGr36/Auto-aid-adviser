package com.hillel.evo.adviser.facets;

import org.hibernate.search.query.facet.FacetingRequest;

@FunctionalInterface
public interface FacetingRequestFactory {
    FacetingRequest get();
}
