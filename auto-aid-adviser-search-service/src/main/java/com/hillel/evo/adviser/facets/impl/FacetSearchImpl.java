package com.hillel.evo.adviser.facets.impl;

import com.hillel.evo.adviser.facets.FacetSearch;
import com.hillel.evo.adviser.service.QueryGeneratorService;
import org.hibernate.search.query.facet.Facet;
import org.hibernate.search.query.facet.FacetingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Repository
public class FacetSearchImpl<T> implements FacetSearch<T> {

    private transient QueryGeneratorService searchService;

    @Autowired
    public void setSearchService(QueryGeneratorService searchService) {
        this.searchService = searchService;
    }

    @Override
    @Transactional
    public List<Facet> search(Class<T> clazz, Supplier<FacetingRequest>... facetingRequests) {
        var query = searchService.getQueryBuilder(clazz).all().createQuery();
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query, clazz);
        Arrays.asList(facetingRequests).forEach(f -> jpaQuery.getFacetManager().enableFaceting(f.get()));

        jpaQuery.getResultList();

        List<Facet> facets = new ArrayList<>();
        Arrays.asList(facetingRequests).forEach(f -> facets.addAll(jpaQuery.getFacetManager().getFacets(f.get().getFacetingName())));

        return facets;
    }
}
