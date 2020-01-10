package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.BaseTest;
import com.hillel.evo.adviser.SearchApp;
import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.dto.SearchTextDTO;
import com.hillel.evo.adviser.entity.Aid;
import com.hillel.evo.adviser.service.QueryGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SearchApp.class)
@Sql(value = {"/data-aids.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SearchTest extends BaseTest {

    @Autowired
    private TextSearch<Aid> aidTextSearch;
    @Autowired
    private SpatialSearch<Aid> aidSpatialSearch;
    @Autowired
    private CustomSearch<Aid> aidCustomSearch;

    @Autowired
    private QueryGeneratorService searchService;

    @Autowired
    private HibernateSearchConfig hibernateSearchConfig;

    @Test
    public void TestSearchText() {

        hibernateSearchConfig.reindex(Aid.class);
        var dto = new SearchTextDTO(Aid.class, "name", "BMW");
        var result = aidTextSearch.search(dto);

        assertEquals(1, result.size());
    }

    @Test
    public void TestSearchTextWildcard() {

        hibernateSearchConfig.reindex(Aid.class);
        var dto = new SearchTextDTO(Aid.class, "name", "bm*");
        var result = aidTextSearch.searchWildcard(dto);

        assertEquals(1, result.size());

    }

    @Test
    public void TestSearchTextWildcardUA() {

        hibernateSearchConfig.reindex(Aid.class);
        var searchVal = "Хон";
        var dto = new SearchTextDTO(Aid.class, "name", searchVal.toLowerCase() + "*");
        var result = aidTextSearch.searchWildcard(dto);

        assertEquals(1, result.size());
    }

    @Test
    public void TestSearchSpatial() {

        hibernateSearchConfig.reindex(Aid.class);
        var result = aidSpatialSearch.search(Aid.class, 100, 11.125, 12.365);

        assertEquals(3, result.size());
    }

    @Test
    public void TestSearchCustom() {

        hibernateSearchConfig.reindex(Aid.class);

        var queryText = searchService.getTextQuery(Aid.class, "name", "Honda");
        var querySpatial = searchService.getSpatialQuery(Aid.class, 100, 11.125, 12.365);

        var result = aidCustomSearch.search(Aid.class, queryText, querySpatial);

        assertEquals(1, result.size());
    }
}
