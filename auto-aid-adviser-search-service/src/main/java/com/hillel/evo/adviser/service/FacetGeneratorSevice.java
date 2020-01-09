package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.facets.FacetingRequestFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
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
        return Search.getFullTextEntityManager(entityManager);
    }

    public QueryBuilder getQueryBuilder(Class clazz) {
        return getFullTextEntityManager().getSearchFactory()
                .buildQueryBuilder().forEntity(clazz).get();
    }

    /**
     * Return Faceting Request Factory.
     * @param clazz entity class
     * @param name Faceting Request name
     * @param field entity field which we use for faceting
     * @param ranges it is using for range faceting requests, what help us to split facets on pieces
     *
     * Discrete Facets example:
     *
     * we have car entities with field model: BMW, Honda, Kia, BMW, BMW, Kia
     * after querying that we will have 3 facets: BMW(3), Honda(1), Kia(2)
     *
     * Range Facets example:
     *
     * we have car entities with field price: 8.999, 11.100, 17.999, 48.999
     * after querying that using 2 ranges(10.000 and 30.000) we will have 3 facets: below 10.000(1), from 10.000 to 30.000(2) and above 30.000(1)
     *
     * in our case we have almost identical method parameter list for Discrete and Range faceting requests, so in case
     *    we are invoking method w/o ranges DiscreteFacetingRequest will be generated, in another case RangeFacetingRequest
     */
    public FacetingRequestFactory getFacetingRequest(final Class clazz, final String name, final String field, Object... ranges) {

        if (ranges.length == 0) {
            return getDiscreteFacetingRequest(clazz, name, field);
        } else {
            return getRangeFacetingRequest(clazz, name, field, ranges);
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

/*
    private FacetingRequestFactory getDiscreteFacetingRequest(final Class clazz, final String name, final String field, Object below, Object above) {
        return () -> getQueryBuilder(clazz)
                .facet()
                .name(name)
                .onField(field)
                .range()
                .below(below)
                .from(below).to(above)
                .above(above)
                .createFacetingRequest();
    }
*/

    /**
     * Return Faceting Request Factory.
     * @param clazz entity class
     * @param name Faceting Request name
     * @param field entity field which we use for faceting
     * @param ranges it is using for range faceting requests, what help us to split facets on pieces
     *
     * Range Facets example:
     * in general range faceting request should be like this:
     *     private FacetingRequestFactory getDiscreteFacetingRequest(final Class clazz, final String name, final String field, Object below, Object above) {
     *          return () -> getQueryBuilder(clazz)
     *                 .facet()
     *                 .name(name)
     *                 .onField(field)
     *                 .range()
     *                 .below(below)
     *                 .from(below).to(above)
     *                 .above(above)
     *                 .createFacetingRequest();
     *     }
     *
     * but what to do in case if we want to have different range amount? create many methods like
     *     private FacetingRequestFactory getDiscreteFacetingRequest(final Class clazz, final String name, final String field, Object below, Object above)
     *     private FacetingRequestFactory getDiscreteFacetingRequest(final Class clazz, final String name, final String field, Object below, Object range, Object above)
     *     private FacetingRequestFactory getDiscreteFacetingRequest(final Class clazz, final String name, final String field, Object below, Object range1, Object range2, Object above)
     *
     *  it doesnt looks convenient.
     *
     *  for handling this we have handleRangeContext() method
     */
    private FacetingRequestFactory getRangeFacetingRequest(final Class clazz, final String name, final String field, Object... ranges) {
        return () -> {
            var rangeContext = getQueryBuilder(clazz)
                    .facet()
                    .name(name)
                    .onField(field)
                    .range();

            return handleRangeContext(rangeContext, ranges)
                    .excludeLimit()
                    .createFacetingRequest();
        };
    }


    /**
     * Return FacetRangeAboveContext.
     * @param rangeContext it is first part of facet request:
     *                     getQueryBuilder(clazz)
     *                     .facet()
     *                     .name(name)
     *                     .onField(field)
     *                     .range();
     *
     * @param ranges it is using for range faceting requests, what help us to split facets on pieces
     *
     * in case we have only 1 range we will need to add only:
     *               .below(range)
     *               .above(range)
     * w/o even from .. to part
     *
     * in case we have 2 ranges we will need to add classic block:
     *               .below(range1)
     *               .from(range1).to(range2)
     *               .above(range2)
     *
     * in case we have more than 2 ranges we need to add diff amount of from .. to blocks:
     *               .below(range1)
     *               .from(range1).to(range2)
     *               .from(range2).to(range3)
     *               .......
     *               .from(range[n-1]).to(range[n])
     *               .above(range[n])
     *
     * for handling this we have handleFromToRanges() method
     */
    private FacetRangeAboveContext handleRangeContext(FacetRangeAboveBelowContext<Object> rangeContext, Object... ranges) {
        switch (ranges.length) {
            case 1:
                return rangeContext.below(ranges[0]).above(ranges[0]);
            case 2:
                return rangeContext.below(ranges[0]).from(ranges[0]).to(ranges[1]).above(ranges[1]);
            default:
                return handleFromToRanges(rangeContext.below(ranges[0]).from(ranges[0]), ranges)
                        .to(ranges[ranges.length - 1])
                        .above(ranges[ranges.length - 1]);
        }
    }

    /**
     * Return FacetRangeLimitContext.
     * @param from it is first from part form many from .. to splitters by ranges
     * @param ranges it is using for range faceting requests, what help us to split facets on pieces
     *
     * this method join all ranges array to 1 chain, it use ranges from 1 to ranges.length - 1, because 1 one it is below, last one it is above.
     */
    private FacetRangeLimitContext handleFromToRanges(FacetRangeLimitContext from, Object... ranges) {

        for (int i = 1; i < ranges.length - 1; i++) {
            from = from.to(ranges[i]).from(ranges[i]);
        }
        return from;
    }

}
