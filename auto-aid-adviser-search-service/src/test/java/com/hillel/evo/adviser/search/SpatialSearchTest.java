package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.SearchApp;
import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.dto.SearchSpatialDTO;
import com.hillel.evo.adviser.dto.SearchTextDTO;
import com.hillel.evo.adviser.entity.Aid;
import com.hillel.evo.adviser.service.QueryGeneratorService;
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
public class SpatialSearchTest {

    @Autowired
    private SpatialSearch<Aid> aidSpatialSearch;

    @Autowired
    private HibernateSearchConfig hibernateSearchConfig;

    @Test
    public void TestSearchSpatial() {

        hibernateSearchConfig.reindex(Aid.class);
        var dto = new SearchSpatialDTO(Aid.class, 100, 11.125, 12.365);
        var result = aidSpatialSearch.search(dto);

        assertEquals(3, result.size());
    }
}
