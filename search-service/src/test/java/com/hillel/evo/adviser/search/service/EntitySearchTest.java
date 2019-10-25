package com.hillel.evo.adviser.search.service;

import com.hillel.evo.adviser.search.SearchApp;
import com.hillel.evo.adviser.search.entity.Car;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceContext;
import java.util.AbstractMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApp.class)
@AutoConfigureTestEntityManager
public class EntitySearchTest {

    @Autowired
    private TestEntityManager testEntityManager;

    private TextSearch<Car> carTextSearch;

    @Autowired
    public void setCarTextSearch(EntitySearch<Car> carTextSearch) {
        this.carTextSearch = carTextSearch;
    }

    @Test
    public void TestSearchText() {

        var result = carTextSearch.search(Car.class, new AbstractMap.SimpleEntry<>("name", "BMW"));

        //Assert.assertThat();

    }
}
