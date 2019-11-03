package com.hillel.evo.adviser.configuration;

import com.hillel.evo.adviser.exception.HibernateSearchIndexException;
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

    private transient Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @PersistenceContext
    private transient EntityManager entityManager;

    @PostConstruct
    @Transactional
    public void reindex() {
        if (Boolean.parseBoolean(environment.getProperty("spring.jpa.properties.hibernate.search.reindex", "false"))) {
            reindex(Object.class);
        }
    }

    @Transactional
    public void reindex(Class clazz) {

        var fullTextEntityManager = Search.getFullTextSession((Session)entityManager.getDelegate());
        try {
            fullTextEntityManager.createIndexer(clazz).startAndWait();
        } catch (InterruptedException e) {
            throw new HibernateSearchIndexException("An error occurred trying to build the serach index", e);
        }
    }
}
