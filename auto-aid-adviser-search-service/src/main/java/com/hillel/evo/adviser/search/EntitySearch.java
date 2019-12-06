package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.service.SearchHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public class EntitySearch<T> implements TextSearch<T>, SpatialSearch<T>, CustomSearch<T> {

    private transient SearchHelperService searchService;

    @Autowired
    public void setSearchService(SearchHelperService searchService) {
        this.searchService = searchService;
    }

    @Override
    public List<T> search(Class<T> clazz, String field, String param) {

        var query = searchService.getTextQuery(clazz, field, param);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query.get(), clazz);
        return jpaQuery.getResultList();
    }

    @Override
    public List<T> searchWildcard(Class<T> clazz, String field, String param) {
        var query = searchService.getTextWildcardQuery(clazz, field, param);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query.get(), clazz);
        return jpaQuery.getResultList();
    }

    @Override
    public List<T> search(Class<T> clazz, double radius, double latitude, double longitude) {
        var query = searchService.getSpatialQuery(clazz, radius, latitude, longitude);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query.get(), clazz);
        return jpaQuery.getResultList();
    }

    @Override
    public List<T> search(Class<T> clazz, QueryFactory... queries) {
        var junction = searchService.getQueryBuilder(clazz).bool();
        Arrays.asList(queries).forEach(q -> junction.must(q.get()));
        var query = junction.createQuery();
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query, clazz);
        return jpaQuery.getResultList();
    }


    public List<T> search(Class<T> clazz, FacetingRequestFactory... facetingRequests) {
        var query = searchService.getQueryBuilder(clazz).all().createQuery();
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query, clazz);
        var facetManager = jpaQuery.getFacetManager();
        Arrays.asList(facetingRequests).forEach(f -> facetManager.enableFaceting(f.get()));

        jpaQuery.getResultList();


        return jpaQuery.getResultList();
    }
}
