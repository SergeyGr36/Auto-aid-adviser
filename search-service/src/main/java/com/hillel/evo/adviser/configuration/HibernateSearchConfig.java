package com.hillel.evo.adviser.configuration;

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
    public void reindex() {
        boolean reindex = Boolean.parseBoolean(environment.getProperty("spring.jpa.properties.hibernate.search.reindex", "false"));
        reindex(reindex, Object.class);
    }

    @Transactional
    public void reindex(boolean reindex, Class clazz) {

        if (reindex) {
            var fullTextEntityManager = Search.getFullTextSession((Session)entityManager.getDelegate());
            try {
                fullTextEntityManager.createIndexer(clazz).startAndWait();
            } catch (InterruptedException e) {
                System.out.println(
                        "An error occurred trying to build the serach index: " +
                                e.toString());
            }
        }
    }



}
