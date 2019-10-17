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
    private EntityManager entityManager;

    FullTextEntityManager fullTextEntityManager;
    QueryBuilder queryBuilder;


    public AidSearch() {
        fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Aid.class).get();

    }

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

        Query query = queryBuilder.spatial()
                .within( radius, Unit.KM )
                .ofLatitude( centerLatitude )
                .andLongitude( centerLongitude )
                .createQuery();

        FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Aid.class);

        List results = jpaQuery.getResultList();

        return results;
    }
}
