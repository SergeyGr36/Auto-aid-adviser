package com.hillel.evo.adviser.search.impl;

import com.hillel.evo.adviser.dto.SearchTextDTO;
import com.hillel.evo.adviser.search.TextSearch;
import com.hillel.evo.adviser.service.QueryGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TextSearchImpl<T> implements TextSearch<T> {

    private transient QueryGeneratorService searchService;

    @Autowired
    public void setSearchService(QueryGeneratorService searchService) {
        this.searchService = searchService;
    }

    @Override
    @Transactional
    public List<T> search(final SearchTextDTO dto) {

        var query = searchService.getTextQuery(dto);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query.get(), dto.getClazz());
        return jpaQuery.getResultList();
    }

    @Override
    @Transactional
    public List<T> searchWildcard(final SearchTextDTO dto) {
        var query = searchService.getTextWildcardQuery(dto);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query.get(), dto.getClazz());
        return jpaQuery.getResultList();
    }
}
