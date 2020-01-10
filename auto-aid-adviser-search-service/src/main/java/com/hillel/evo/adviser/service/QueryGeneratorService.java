package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.dto.SearchSpatialDTO;
import com.hillel.evo.adviser.dto.SearchSpatialLocationDTO;
import com.hillel.evo.adviser.dto.SearchTextDTO;
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

    public QueryBuilder getQueryBuilder(final Class clazz) {
        return getFullTextEntityManager().getSearchFactory()
                .buildQueryBuilder().forEntity(clazz).get();
    }

    public Supplier<Query> getTextQuery(final SearchTextDTO dto) {
        return () -> getQueryBuilder(dto.getClazz())
                .keyword()
                .onField(dto.getField())
                .matching(dto.getParam())
                .createQuery();
    }

    public Supplier<Query> getTextWildcardQuery(final SearchTextDTO dto) {
        return () -> getQueryBuilder(dto.getClazz())
                .keyword()
                .wildcard()
                .onField(dto.getField())
                .matching(dto.getParam())
                .createQuery();
    }

    public Supplier<Query> getSpatialQuery(final SearchSpatialDTO dto) {
        return () -> getQueryBuilder(dto.getClazz())
                .spatial()
                .within(dto.getRadius(), Unit.KM)
                .ofLatitude(dto.getLatitude())
                .andLongitude(dto.getLongitude())
                .createQuery();
    }

    public Supplier<Query> getSpatialQuery(final SearchSpatialLocationDTO dto) {
        return () -> getQueryBuilder(dto.getClazz())
                .spatial()
                .onField(dto.getField())
                .within(dto.getRadius(), Unit.KM)
                .ofLatitude(dto.getLatitude())
                .andLongitude(dto.getLongitude())
                .createQuery();
    }
}
