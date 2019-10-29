package com.hillel.evo.adviser.service;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.Unit;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class SearchService {

    @PersistenceContext
    private transient EntityManager entityManager;

    public FullTextEntityManager getFullTextEntityManager() {
        return org.hibernate.search.jpa.Search.
                getFullTextEntityManager(entityManager);
    }

    public QueryBuilder getQueryBuilder(Class clazz) {
        return getFullTextEntityManager().getSearchFactory()
                .buildQueryBuilder().forEntity(clazz).get();
    }

    public Query getTextQuery(Class clazz, String field, String value) {
        return getQueryBuilder(clazz)
                .keyword()
                .onField(field)
                .matching(value)
                .createQuery();
    }

    public Query getSpatialQuery(Class clazz, double radius, double latitude, double longitude) {
        return getQueryBuilder(clazz)
                .spatial()
                .within( radius, Unit.KM )
                .ofLatitude( latitude )
                .andLongitude( longitude )
                .createQuery();
    }
}
