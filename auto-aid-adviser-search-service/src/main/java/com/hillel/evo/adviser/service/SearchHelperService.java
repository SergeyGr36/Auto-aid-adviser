package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.search.QueryFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.Unit;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class SearchHelperService {

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

    public QueryFactory getTextQuery(final Class clazz, final String field, final String value) {
        QueryFactory result = () -> getQueryBuilder(clazz)
                .keyword()
                .onField(field)
                .matching(value)
                .createQuery();

        return result;
    }

    public QueryFactory getTextWildcardQuery(final Class clazz, final String field, final String value) {
        QueryFactory result = () -> getQueryBuilder(clazz)
                .keyword()
                .wildcard()
                .onField(field)
                .matching(value)
                .createQuery();

        return result;
    }

    public QueryFactory getSpatialQuery(Class clazz, double radius, double latitude, double longitude) {
        QueryFactory result = () -> getQueryBuilder(clazz)
                .spatial()
                .within(radius, Unit.KM)
                .ofLatitude(latitude)
                .andLongitude(longitude)
                .createQuery();
        return result;


    }
}