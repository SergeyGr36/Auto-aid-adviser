package com.hillel.evo.adviser.search.service;


//import javafx.util.Pair;
import org.hibernate.search.query.dsl.Unit;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.AbstractMap;
import java.util.List;

@Repository
@Transactional
public class EntitySearch<T> implements TextSearch<T>, SpatialSearch<T> {

    @PersistenceContext
    private transient EntityManager entityManager;

    @Override
    public List<T> search(Class<T> clazz, AbstractMap.SimpleEntry<String, String> pair) {

        var fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        var queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(clazz).get();

        var query =
                queryBuilder
                        .keyword()
                        .onFields(pair.getKey())
                        .matching(pair.getValue())
                        .createQuery();

        var jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, clazz);

        return jpaQuery.getResultList();
    }

    @Override
    public List<T> searchAll(Class<T> clazz, List<AbstractMap.SimpleEntry<String, String>> list) {
        var fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        var queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(clazz).get();

        var junction = queryBuilder.bool();
        for (var pair : list) {
            var q =
                    queryBuilder
                            .keyword()
                            .onFields(pair.getKey())
                            .matching(pair.getValue())
                            .createQuery();
            junction.must(q);
        }
        var query = junction.createQuery();

        var jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, clazz);

        return jpaQuery.getResultList();
    }

    @Override
    public List<T> searchAny(Class<T> clazz, List<AbstractMap.SimpleEntry<String, String>> list) {
        var fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        var queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(clazz).get();

        var junction = queryBuilder.bool();
        for (var pair : list) {
            var q =
                    queryBuilder
                            .keyword()
                            .onFields(pair.getKey())
                            .matching(pair.getValue())
                            .createQuery();
            junction.should(q);
        }
        var query = junction.createQuery();

        var jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, clazz);

        return jpaQuery.getResultList();
    }

    @Override
    public List<T> search(Class<T> clazz, double radius, double latitude, double longitude) {
        var fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        var queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(clazz).get();

        var query = queryBuilder.spatial()
                .within( radius, Unit.KM )
                .ofLatitude( latitude )
                .andLongitude( longitude )
                .createQuery();

        var jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, clazz);

        return jpaQuery.getResultList();
    }

    @Override
    public List<T> searchAll(Class<T> clazz, List<AbstractMap.SimpleEntry<String, String>> list, double radius, double latitude, double longitude) {
        var fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        var queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(clazz).get();

        var spatialQuery = queryBuilder.spatial()
                .within( radius, Unit.KM )
                .ofLatitude( latitude )
                .andLongitude( longitude )
                .createQuery();

        var junction = queryBuilder.bool();
        for (var pair : list) {
            var q =
                    queryBuilder
                            .keyword()
                            .onFields(pair.getKey())
                            .matching(pair.getValue())
                            .createQuery();
            junction.must(q);
        }
        var query = junction.must(spatialQuery).createQuery();

        var jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, clazz);

        return jpaQuery.getResultList();
    }

    //@Override
    public List<T> searchAny(Class<T> clazz, List<AbstractMap.SimpleEntry<String, String>> list, double radius, double latitude, double longitude) {
        var fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        var queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(clazz).get();

        var spatialQuery = queryBuilder.spatial()
                .within( radius, Unit.KM )
                .ofLatitude( latitude )
                .andLongitude( longitude )
                .createQuery();

        var junction = queryBuilder.bool();
        for (var pair : list) {
            var q =
                    queryBuilder
                            .keyword()
                            .onFields(pair.getKey())
                            .matching(pair.getValue())
                            .createQuery();
            junction.should(q);
        }
        var query = junction.must(spatialQuery).createQuery();

        var jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, clazz);

        return jpaQuery.getResultList();
    }
}
