package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.BaseTest;
import com.hillel.evo.adviser.SearchApp;
import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.entity.Aid;
import com.hillel.evo.adviser.service.QueryGeneratorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SearchApp.class)
@AutoConfigureTestEntityManager
public class SearchTest extends BaseTest {

    private TextSearch<Aid> aidTextSearch;
    private SpatialSearch<Aid> aidSpatialSearch;
    private CustomSearch<Aid> aidCustomSearch;

    private QueryGeneratorService searchService;

    @Autowired
    public void setAidTextSearch(TextSearch<Aid> aidTextSearch) {
        this.aidTextSearch = aidTextSearch;
    }

    @Autowired
    public void setAidSpatialSearch(SpatialSearch<Aid> aidSpatialSearch) {
        this.aidSpatialSearch = aidSpatialSearch;
    }

    @Autowired
    public void setAidCustomSearch(CustomSearch<Aid> aidCustomSearch) {
        this.aidCustomSearch = aidCustomSearch;
    }

    @Autowired
    public void setSearchService(QueryGeneratorService searchService) {
        this.searchService = searchService;
    }

    private HibernateSearchConfig hibernateSearchConfig;

    @Autowired
    public void setConfig(HibernateSearchConfig config) {
        this.hibernateSearchConfig = config;
    }

    @BeforeAll
    static void setUp() {

    }

    @Test
    @Sql({"/data-aids.sql"})
    public void TestSearchText() {

        hibernateSearchConfig.reindex(Aid.class);
        var result = aidTextSearch.search(Aid.class, "name", "BMW");

        assertEquals(1, result.size());
    }

    @Test
    @Sql({"/data-aids.sql"})
    public void TestSearchTextWildcard() {

        hibernateSearchConfig.reindex(Aid.class);
        var result = aidTextSearch.searchWildcard(Aid.class, "name", "bm*");

        assertEquals(1, result.size());

    }

    @Test
    @Sql({"/data-aids.sql"})
    public void TestSearchTextWildcardUA() {

        hibernateSearchConfig.reindex(Aid.class);
        var searchVal = "Хон";
        var result = aidTextSearch.searchWildcard(Aid.class, "name", searchVal.toLowerCase() + "*");

        assertEquals(0, result.size());
    }

    @Test
    @Sql({"/data-aids.sql"})
    public void TestSearchSpatial() {

        hibernateSearchConfig.reindex(Aid.class);
        var result = aidSpatialSearch.search(Aid.class, 100, 11.125, 12.365);

        assertEquals(3, result.size());
    }

    @Test
    @Sql({"/data-aids.sql"})
    public void TestSearchCustom() {

        hibernateSearchConfig.reindex(Aid.class);

        var queryText = searchService.getTextQuery(Aid.class, "name", "Honda");
        var querySpatial = searchService.getSpatialQuery(Aid.class, 100, 11.125, 12.365);

        var result = aidCustomSearch.search(Aid.class, queryText, querySpatial);

        assertEquals(1, result.size());
    }


}
