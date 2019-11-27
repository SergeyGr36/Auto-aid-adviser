package com.hillel.evo.adviser;

import io.hypersistence.optimizer.HypersistenceOptimizer;
import io.hypersistence.optimizer.core.config.JpaConfig;
import org.junit.jupiter.api.Test;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class BaseTest {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test // поставил @Test потому что с @PostConstructor HypersistenceOptimizer не дает открыть транзакцию
    public void initOptimizer() {
        new HypersistenceOptimizer(
                new JpaConfig(entityManagerFactory)
        ).init();
    }
}
