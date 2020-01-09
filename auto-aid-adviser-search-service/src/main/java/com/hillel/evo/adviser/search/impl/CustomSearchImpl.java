package com.hillel.evo.adviser.search.impl;

import com.hillel.evo.adviser.search.CustomSearch;
import com.hillel.evo.adviser.service.QueryGeneratorService;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Repository
public class CustomSearchImpl<T> implements CustomSearch<T> {

    private transient QueryGeneratorService searchService;

    @Autowired
    public void setSearchService(QueryGeneratorService searchService) {
        this.searchService = searchService;
    }

    @Override
    @Transactional
    public List<T> search(Class<T> clazz, Supplier<Query>... queries) {
        var junction = searchService.getQueryBuilder(clazz).bool();
        Arrays.asList(queries).forEach(q -> junction.must(q.get()));
        var query = junction.createQuery();
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query, clazz);
        return jpaQuery.getResultList();
    }
}
