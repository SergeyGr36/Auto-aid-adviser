package com.hillel.evo.adviser.search.configuration;

import org.hibernate.Session;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class HibernateSearchConfig {

    @Autowired
    private transient Environment environment;

    @PersistenceContext
    private transient EntityManager entityManager;

    @PostConstruct
    @Transactional
    public void reindex() {
        if (Boolean.parseBoolean(environment.getProperty("spring.jpa.properties.hibernate.search.reindex", "false"))) {
            var fullTextEntityManager = Search.getFullTextSession((Session)entityManager.getDelegate());
            try {
                fullTextEntityManager.createIndexer().startAndWait();
            } catch (InterruptedException e) {
                System.out.println(
                        "An error occurred trying to build the serach index: " +
                                e.toString());
            }
        }
    }

}
