package com.hillel.evo.adviser.search.dao;

import com.hillel.evo.adviser.search.entity.Aid;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.Unit;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class AidSearch {

    @PersistenceContext
    private transient EntityManager entityManager;

//    public List<Aid> searchByType(String type) {
//
//        Query query =
//                queryBuilder
//                        .keyword()
//                        .onFields("type")
//                        .matching(type)
//                        .createQuery();
//
//        FullTextQuery jpaQuery =
//                fullTextEntityManager.createFullTextQuery(query, Aid.class);
//
//        List results = jpaQuery.getResultList();
//
//        return results;
//    }

    public List<Aid> search(String type, double radius, double centerLatitude, double centerLongitude) {

        var fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        var queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Aid.class).get();

        var querySpatial = queryBuilder.spatial()
                .within( radius, Unit.KM )
                .ofLatitude( centerLatitude )
                .andLongitude( centerLongitude )
                .createQuery();

        var queryType = queryBuilder.keyword()
                .onField("type")
                .matching(type)
                .createQuery();

        var query = queryBuilder.bool()
                .must(querySpatial)
                .must(queryType)
                .createQuery();

        var jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Aid.class);

        var results = jpaQuery.getResultList();

        return results;
    }
}
