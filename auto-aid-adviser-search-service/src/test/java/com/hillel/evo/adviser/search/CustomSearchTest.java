package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.SearchApp;
import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.dto.SearchCustomDTO;
import com.hillel.evo.adviser.dto.SearchSpatialDTO;
import com.hillel.evo.adviser.dto.SearchTextDTO;
import com.hillel.evo.adviser.entity.Aid;
import com.hillel.evo.adviser.mapper.AidMapper;
import com.hillel.evo.adviser.service.QueryGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SearchApp.class)
@AutoConfigureTestEntityManager
@Sql(value = {"/data-aids.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CustomSearchTest {

    @Autowired
    private CustomSearch<Aid> aidCustomSearch;

    @Autowired
    private QueryGeneratorService searchService;

    @Autowired
    private HibernateSearchConfig hibernateSearchConfig;

    @Autowired
    private AidMapper aidMapper;

    @Test
    public void TestSearchCustom() {

        hibernateSearchConfig.reindex(Aid.class);

        var textDTO = new SearchTextDTO(Aid.class, "name", "Honda");
        var queryText = searchService.getTextQuery(textDTO);
        var spatialDTO = new SearchSpatialDTO(Aid.class, 100, 11.125, 12.365);
        var querySpatial = searchService.getSpatialQuery(spatialDTO);
        var dto = new SearchCustomDTO(Aid.class, new ArrayList<>());
        dto.getQueries().add(querySpatial);
        dto.getQueries().add(queryText);
        var result = aidCustomSearch.search(aidMapper, dto);

        assertEquals(1, result.size());
    }
}
