package com.hillel.evo.adviser.facets;

import com.hillel.evo.adviser.service.QueryGeneratorService;
import org.hibernate.search.query.facet.Facet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class FacetSearchImp<T> implements FacetSearch<T> {

    private transient QueryGeneratorService searchService;

    @Autowired
    public void setSearchService(QueryGeneratorService searchService) {
        this.searchService = searchService;
    }

    @Override
    public List<Facet> search(Class<T> clazz, FacetingRequestFactory... facetingRequests) {
        var query = searchService.getQueryBuilder(clazz).all().createQuery();
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query, clazz);
        Arrays.asList(facetingRequests).forEach(f -> jpaQuery.getFacetManager().enableFaceting(f.get()));

        jpaQuery.getResultList();

        List<Facet> facets = new ArrayList<>();
        Arrays.asList(facetingRequests).forEach(f -> facets.addAll(jpaQuery.getFacetManager().getFacets(f.get().getFacetingName())));

        return facets;
    }
}
