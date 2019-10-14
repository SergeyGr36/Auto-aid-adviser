package com.hillel.evo.adviser.search.dao;

import com.hillel.evo.adviser.search.dto.InputSearchDto;
import com.hillel.evo.adviser.search.entity.Aid;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
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

    public List<Aid> searchByType(String type) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Aid.class).get();

        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .onFields("type")
                        .matching(type)
                        .createQuery();

        FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Aid.class);

        List results = jpaQuery.getResultList();

        return results;
    }

    public List<Aid> search(InputSearchDto searParams) {

        return null;
    }
}
