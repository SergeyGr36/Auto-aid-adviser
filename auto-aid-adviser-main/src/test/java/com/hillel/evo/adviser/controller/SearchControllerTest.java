//package com.hillel.evo.adviser.controller;
//
//
//import com.hillel.evo.adviser.search.service.AidSearchService;
//import com.hillel.evo.adviser.starter.AdviserStarter;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.platform.runner.JUnitPlatform;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////@RunWith(JUnitPlatform.class)
////@SpringBootTest(classes = AdviserStarter.class)
//public class SearchControllerTest {
//
//    private MockMvc mockMvc;
//
//    private SearchController searchController;
//
//
//    @BeforeEach
//    public void setUp() {
//
//        searchController = new SearchController();
//        //AidSearchService searchService;
//        AidSearchService searchService = new AidSearchService();
//        searchController.setSearchService(searchService);
//
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(searchController)
//                .build();
//    }
//
//    @Test
//    public void testSearchController() throws Exception {
//
//        String type = "sto";
//        double lat = 12.3;
//        String lon = "sdfkjha";
//
//        MvcResult mvcResult = this.mockMvc
//                .perform(get("search")
//                        .param("type", type)
//                        .param("lat", String.valueOf(lat))
//                        .param("lon", String.valueOf(lon))
//                )
//                .andReturn();
//
//        assertThat(status().isBadRequest());
//
//
//    }
//}
