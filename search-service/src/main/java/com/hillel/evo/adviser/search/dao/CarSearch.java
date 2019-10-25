package com.hillel.evo.adviser.search.dao;

import com.hillel.evo.adviser.search.entity.Car;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class CarSearch {

    @PersistenceContext
    private EntityManager entityManager;

    public List search(String text) {

        var fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        var queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Car.class).get();

        var query =
                queryBuilder
                        .keyword()
                        .onFields("name")
                        .matching(text)
                        .createQuery();

        var jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Car.class);

        List results = jpaQuery.getResultList();

        return results;
    }
}
