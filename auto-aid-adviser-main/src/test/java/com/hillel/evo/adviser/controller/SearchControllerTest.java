package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.entity.Business;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/clean-all.sql", "/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SearchControllerTest {

    private static final String PATH_SEARCH =  "/search";

    @Autowired
    private HibernateSearchConfig hibernateSearchConfig;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findByServiceAndLocationDefaultRadiusThenReturnOK() throws Exception {
        hibernateSearchConfig.reindex(Business.class);
        mockMvc.perform(get(PATH_SEARCH+"?service=balancing&latitude=50.0&longitude=50.0"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByServiceAndLocationThenReturnOK() throws Exception {
        hibernateSearchConfig.reindex(Business.class);
        mockMvc.perform(get(PATH_SEARCH+"?service=balancing&latitude=50.0&longitude=50.0&radius=10.0"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByServiceNotExistAndLocationThenReturnNotFound() throws Exception {
        hibernateSearchConfig.reindex(Business.class);
        mockMvc.perform(get(PATH_SEARCH+"?service=kolobok/&latitude=50.0&longitude=50.0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByServiceAndLocationNotExistThenReturnNotFound() throws Exception {
        hibernateSearchConfig.reindex(Business.class);
        mockMvc.perform(get(PATH_SEARCH+"?service=balancing&latitude=150.0&longitude=50.0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByServiceAndBadLocationThenReturnBadRequest() throws Exception {
        mockMvc.perform(get(PATH_SEARCH+"?service=balancing&latitude=test&longitude=50.0"))
                .andExpect(status().isBadRequest());
    }
}
