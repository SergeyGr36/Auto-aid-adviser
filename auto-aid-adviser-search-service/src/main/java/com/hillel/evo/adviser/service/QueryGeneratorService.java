package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.search.QueryFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.Unit;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class QueryGeneratorService {

    @PersistenceContext
    private transient EntityManager entityManager;

    public FullTextEntityManager getFullTextEntityManager() {
        return Search.getFullTextEntityManager(entityManager);
    }

    public QueryBuilder getQueryBuilder(Class clazz) {
        return getFullTextEntityManager().getSearchFactory()
                .buildQueryBuilder().forEntity(clazz).get();
    }

    public QueryFactory getTextQuery(final Class clazz, final String field, final String value) {
        return () -> getQueryBuilder(clazz)
                .keyword()
                .onField(field)
                .matching(value)
                .createQuery();
    }

    public QueryFactory getTextWildcardQuery(final Class clazz, final String field, final String value) {
        return () -> getQueryBuilder(clazz)
                .keyword()
                .wildcard()
                .onField(field)
                .matching(value)
                .createQuery();
    }

    public QueryFactory getSpatialQuery(final Class clazz, final double radius, final double latitude, double longitude) {
        return () -> getQueryBuilder(clazz)
                .spatial()
                .within( radius, Unit.KM )
                .ofLatitude( latitude )
                .andLongitude( longitude )
                .createQuery();
    }

    public QueryFactory getSpatialQuery(final Class clazz, String fieldLocation, final double radius, final double latitude, double longitude) {
        return () -> getQueryBuilder(clazz)
                .spatial()
                .onField(fieldLocation)
                .within( radius, Unit.KM )
                .ofLatitude( latitude )
                .andLongitude( longitude )
                .createQuery();
    }
}
