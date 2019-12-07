package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.facets.FacetingRequestFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.FacetRangeAboveBelowContext;
import org.hibernate.search.query.dsl.FacetRangeAboveContext;
import org.hibernate.search.query.dsl.FacetRangeLimitContext;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.facet.FacetSortOrder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class FacetGeneratorSevice {

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

    public FacetingRequestFactory getFacetingRequest(final Class clazz, final String name, final String field, Object... range) {

        if (range.length == 0) {
            return getDiscreteFacetingRequest(clazz, name, field);
        } else {
            return getRangeFacetingRequest(clazz, name, field, range);
        }
    }

    private FacetingRequestFactory getDiscreteFacetingRequest(final Class clazz, final String name, final String field) {
        return () -> getQueryBuilder(clazz)
                .facet()
                .name(name)
                .onField(field)
                .discrete()
                .orderedBy(FacetSortOrder.COUNT_DESC)
                .includeZeroCounts(true)
                .createFacetingRequest();
    }

    private FacetingRequestFactory getRangeFacetingRequest(final Class clazz, final String name, final String field, Object... range) {
        return () -> {
            var rangeContex = getQueryBuilder(clazz)
                    .facet()
                    .name(name)
                    .onField(field)
                    .range();

            return getAboveContext(rangeContex, range)
                    .excludeLimit()
                    .createFacetingRequest();
        };
    }

    private FacetRangeAboveContext getAboveContext(FacetRangeAboveBelowContext<Object> rangeContext, Object... range) {
        switch (range.length) {
            case 1:
                return rangeContext.below(range[0]).above(range[0]);
            case 2:
                return rangeContext.below(range[0]).from(range[0]).to(range[1]).above(range[1]);
            default:
                return getJoinedRange(rangeContext.below(range[0]).from(range[0]), range)
                        .to(range[range.length - 1])
                        .above(range[range.length - 1]);
        }
    }

    private FacetRangeLimitContext getJoinedRange(FacetRangeLimitContext from, Object... range) {

        for (int i = 1; i < range.length - 1; i++) {
            from = from.to(range[i]).from(range[i]);
        }
        return from;
    }

}
