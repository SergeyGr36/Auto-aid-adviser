package com.hillel.evo.adviser.search.impl;

import com.hillel.evo.adviser.search.SpatialSearch;
import com.hillel.evo.adviser.service.QueryGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SpatialSearchImpl<T> implements SpatialSearch<T> {

    private transient QueryGeneratorService searchService;

    @Autowired
    public void setSearchService(QueryGeneratorService searchService) {
        this.searchService = searchService;
    }

    @Override
    @Transactional
    public List<T> search(Class<T> clazz, double radius, double latitude, double longitude) {
        var query = searchService.getSpatialQuery(clazz, radius, latitude, longitude);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query.get(), clazz);
        return jpaQuery.getResultList();
    }
}
