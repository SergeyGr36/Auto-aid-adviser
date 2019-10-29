package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.SearchApp;
import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.entity.Car;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = SearchApp.class)
@AutoConfigureTestEntityManager
public class TextSearchTest {

    private TextSearch<Car> carTextSearch;

    @Autowired
    public void setCarTextSearch(EntitySearch<Car> carTextSearch) {
        this.carTextSearch = carTextSearch;
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
    //@Transactional
    @Sql(value = {"/data-aids.sql"})
    public void TestSearchText() {

        hibernateSearchConfig.reindex(true, Car.class);
        var result = carTextSearch.search(Car.class, "name", "BMW");

        assertEquals(1, result.size());
    }

//    @Test
//    //@Transactional
//    @Sql({"/data-cars.sql"})
//    public void TestSearchTextAll() {
//
//        hibernateSearchConfig.reindex(true, Car.class);
//        List<AbstractMap.SimpleEntry<String, String>> list = new ArrayList<>();
//        list.add(new AbstractMap.SimpleEntry<>("name", "Mersedes"));
//        list.add(new AbstractMap.SimpleEntry<>("name", "Mersedes"));
//
//
//        var result = carTextSearch.searchAll(Car.class, list);
//
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    @Sql({"/data-cars.sql"})
//    public void TestSearchTextAny() {
//
//        hibernateSearchConfig.reindex(true, Car.class);
//        List<AbstractMap.SimpleEntry<String, String>> list = new ArrayList<>();
//        list.add(new AbstractMap.SimpleEntry<>("name", "Honda"));
//        list.add(new AbstractMap.SimpleEntry<>("name", "Kia"));
//
//        var result = carTextSearch.searchAny(Car.class, list);
//
//        assertEquals(2, result.size());
//    }


}
