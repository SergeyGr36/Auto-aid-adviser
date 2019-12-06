package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.search.FacetingRequestFactory;
import com.hillel.evo.adviser.search.QueryFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.Unit;
import org.hibernate.search.query.facet.FacetSortOrder;
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

    public FacetingRequestFactory getDiscreteFacetingRequest(final Class clazz, final String name, final String field) {
        return () -> getQueryBuilder(clazz)
                .facet()
                .name(name)
                .onField(field)
                .discrete()
                .orderedBy(FacetSortOrder.COUNT_DESC)
                .includeZeroCounts(true)
                .createFacetingRequest();
    }

    public FacetingRequestFactory getRangeFacetingRequest(final Class clazz, final String name, final String field, Object... range) {
        return () -> {
            var r = getQueryBuilder(clazz)
                .facet()
                .name(name)
                .onField(field)
                .range()
                .below(range[0]).from(range[0]);

            for (int i = 1; i < range.length - 1; i++) {
                r = r.to(range[i]).from(range[i]);
            }

            var result = r.to(range[range.length - 1])
                .above(range[range.length - 1])
                .excludeLimit()
                .createFacetingRequest();
        return  result;
        };
    }
}
