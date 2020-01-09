package com.hillel.evo.adviser.service;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.Unit;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.function.Supplier;

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

    public Supplier<Query> getTextQuery(final Class<?> clazz, final String field, final String value) {
        return () -> getQueryBuilder(clazz)
                .keyword()
                .onField(field)
                .matching(value)
                .createQuery();
    }

    public Supplier<Query> getTextWildcardQuery(final Class clazz, final String field, final String value) {
        return () -> getQueryBuilder(clazz)
                .keyword()
                .wildcard()
                .onField(field)
                .matching(value)
                .createQuery();
    }

    public Supplier<Query> getSpatialQuery(final Class<?> clazz, final double radius, final double latitude, double longitude) {
        return () -> getQueryBuilder(clazz)
                .spatial()
                .within(radius, Unit.KM)
                .ofLatitude(latitude)
                .andLongitude(longitude)
                .createQuery();
    }

    public Supplier<Query> getSpatialQuery(final Class<?> clazz,
                                        String fieldLocation,
                                        final double radius,
                                        final double latitude,
                                        double longitude) {
        return () -> getQueryBuilder(clazz)
                .spatial()
                .onField(fieldLocation)
                .within(radius, Unit.KM)
                .ofLatitude(latitude)
                .andLongitude(longitude)
                .createQuery();
    }
}
