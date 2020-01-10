package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.SearchApp;
import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.dto.SearchTextDTO;
import com.hillel.evo.adviser.entity.Aid;
import com.hillel.evo.adviser.mapper.AidMapper;
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
public class TextSearchTest {

    @Autowired
    private TextSearch<Aid> aidTextSearch;

    @Autowired
    private HibernateSearchConfig hibernateSearchConfig;

    @Autowired
    private AidMapper aidMapper;

    @Test
    public void TestSearchText() {

        hibernateSearchConfig.reindex(Aid.class);
        var dto = new SearchTextDTO(Aid.class, "name", "BMW");
        var result = aidTextSearch.search(aidMapper, dto);

        assertEquals(1, result.size());
    }

    @Test
    public void TestSearchTextWildcard() {

        hibernateSearchConfig.reindex(Aid.class);
        var dto = new SearchTextDTO(Aid.class, "name", "bm*");
        var result = aidTextSearch.searchWildcard(aidMapper, dto);

        assertEquals(1, result.size());
    }

    @Test
    public void TestSearchTextWildcardUA() {

        hibernateSearchConfig.reindex(Aid.class);
        var searchVal = "Хон";
        var dto = new SearchTextDTO(Aid.class, "name", searchVal.toLowerCase() + "*");
        var result = aidTextSearch.searchWildcard(aidMapper, dto);

        assertEquals(1, result.size());
    }

}
