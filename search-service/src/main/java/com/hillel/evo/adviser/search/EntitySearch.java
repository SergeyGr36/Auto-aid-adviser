package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.service.SearchService;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public class EntitySearch<T> implements TextSearch<T>, SpatialSearch<T>, CustomSearch<T> {

    private SearchService searchService;

    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public List<T> search(Class<T> clazz, String field, String param) {

        var query = searchService.getTextQuery(clazz, field, param);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query, clazz);
        return jpaQuery.getResultList();
    }

//    @Override
//    public List<T> searchAll(Class<T> clazz, List<AbstractMap.SimpleEntry<String, String>> list) {
//
//        var junction = searchService.getQueryBuilder(clazz).bool();
//        for (var pair : list) {
//            var q = searchService.getTextQuery(clazz, pair.getKey(), pair.getValue());
//            junction.must(q);
//        }
//        var query = junction.createQuery();
//        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query, clazz);
//        return jpaQuery.getResultList();
//    }
//
//    @Override
//    public List<T> searchAny(Class<T> clazz, List<AbstractMap.SimpleEntry<String, String>> list) {
//        var junction = searchService.getQueryBuilder(clazz).bool();
//        for (var pair : list) {
//            var q = searchService.getTextQuery(clazz, pair.getKey(), pair.getValue());
//            junction.should(q);
//        }
//        var query = junction.createQuery();
//        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query, clazz);
//        return jpaQuery.getResultList();
//    }

    @Override
    public List<T> search(Class<T> clazz, double radius, double latitude, double longitude) {
        var query = searchService.getSpatialQuery(clazz, radius, latitude, longitude);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query, clazz);
        return jpaQuery.getResultList();
    }

//    @Override
//    public List<T> searchAll(Class<T> clazz, List<AbstractMap.SimpleEntry<String, String>> list, double radius, double latitude, double longitude) {
//        var spatialQuery = searchService.getSpatialQuery(clazz, radius, latitude, longitude);
//        var junction = searchService.getQueryBuilder(clazz).bool();
//        for (var pair : list) {
//            var q = searchService.getTextQuery(clazz, pair.getKey(), pair.getValue());
//            junction.must(q);
//        }
//        var query = junction.must(spatialQuery).createQuery();
//        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query, clazz);
//        return jpaQuery.getResultList();
//    }
//
//    @Override
//    public List<T> searchAny(Class<T> clazz, List<AbstractMap.SimpleEntry<String, String>> list, double radius, double latitude, double longitude) {
//        var spatialQuery = searchService.getSpatialQuery(clazz, radius, latitude, longitude);
//        var junction = searchService.getQueryBuilder(clazz).bool();
//        for (var pair : list) {
//            var q = searchService.getTextQuery(clazz, pair.getKey(), pair.getValue());
//            junction.should(q);
//        }
//        var query = junction.must(spatialQuery).createQuery();
//        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query, clazz);
//        return jpaQuery.getResultList();
//    }

    @Override
    public List<T> search(Class<T> clazz, Query... queries) {
        var junction = searchService.getQueryBuilder(clazz).bool();
        Arrays.asList(queries).forEach(q -> junction.must(q));
        var query = junction.createQuery();
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query, clazz);
        return jpaQuery.getResultList();
    }
}
