package com.hillel.evo.adviser.facets;

import com.hillel.evo.adviser.SearchApp;
import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.entity.Aid;
import com.hillel.evo.adviser.service.FacetGeneratorSevice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SearchApp.class)
@AutoConfigureTestEntityManager
@Sql(value = {"/data-aids.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class FacetSearchTest {

    @Autowired
    private FacetSearch<Aid> aidFacetSearch;

    @Autowired
    private HibernateSearchConfig hibernateSearchConfig;

    @Autowired
    private FacetGeneratorSevice facetGeneratorSevice;

    @Test
    public void shouldReturnFacetsByDiscreteFacetRequest() {

        hibernateSearchConfig.reindex(Aid.class);
        var fr = facetGeneratorSevice.getFacetingRequest(Aid.class, "typeFacet", "type");
        var result = aidFacetSearch.search(Aid.class, fr);

        assertEquals(2, result.size());
    }

    @Test
    public void shouldReturnFacetsByRangeFacetRequestWith1Param() {

        hibernateSearchConfig.reindex(Aid.class);
        var fr = facetGeneratorSevice.getFacetingRequest(Aid.class, "latFacet", "latitude", 10.0);
        var result = aidFacetSearch.search(Aid.class, fr);

        assertEquals(2, result.size());
    }

    @Test
    public void shouldReturnFacetsByRangeFacetRequestWith2Param() {

        hibernateSearchConfig.reindex(Aid.class);
        var fr = facetGeneratorSevice.getFacetingRequest(Aid.class, "latFacet", "latitude", 10.0, 12.0);
        var result = aidFacetSearch.search(Aid.class, fr);

        assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnFacetsByRangeFacetRequestWith3Param() {

        hibernateSearchConfig.reindex(Aid.class);
        var fr = facetGeneratorSevice.getFacetingRequest(Aid.class, "latFacet", "latitude", 10.0, 12.0, 15.0);
        var result = aidFacetSearch.search(Aid.class, fr);

        assertEquals(4, result.size());
    }
}
