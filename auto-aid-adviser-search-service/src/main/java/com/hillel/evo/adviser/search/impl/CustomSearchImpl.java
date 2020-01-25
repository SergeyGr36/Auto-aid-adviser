package com.hillel.evo.adviser.search.impl;

import com.hillel.evo.adviser.dto.SearchCustomDTO;
import com.hillel.evo.adviser.mapper.BaseMapper;
import com.hillel.evo.adviser.search.CustomSearch;
import com.hillel.evo.adviser.service.QueryGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CustomSearchImpl<T> implements CustomSearch<T> {

    private transient QueryGeneratorService searchService;

    @Autowired
    public void setSearchService(QueryGeneratorService searchService) {
        this.searchService = searchService;
    }

    @Override
    @Transactional
    public List<T> search(final BaseMapper mapper, final SearchCustomDTO dto) {
        var junction = searchService.getQueryBuilder(dto.getClazz()).bool();
        dto.getQueries().forEach(q -> junction.must(q.get()));
        var query = junction.createQuery();
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query, dto.getClazz());
        return mapper.toDtoList(jpaQuery.getResultList());
    }
}
