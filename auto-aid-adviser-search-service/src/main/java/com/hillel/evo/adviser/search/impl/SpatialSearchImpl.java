package com.hillel.evo.adviser.search.impl;

import com.hillel.evo.adviser.dto.SearchSpatialDTO;
import com.hillel.evo.adviser.mapper.BaseMapper;
import com.hillel.evo.adviser.search.SpatialSearch;
import com.hillel.evo.adviser.service.QueryGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SpatialSearchImpl<T> implements SpatialSearch<T> {

    private transient QueryGeneratorService searchService;

    @Autowired
    public void setSearchService(QueryGeneratorService searchService) {
        this.searchService = searchService;
    }

    @Override
    @Transactional
    public List<T> search(final BaseMapper mapper, final SearchSpatialDTO dto) {
        var query = searchService.getSpatialQuery(dto);
        var jpaQuery = searchService.getFullTextEntityManager().createFullTextQuery(query.get(), dto.getClazz());
        return mapper.toDtoList(jpaQuery.getResultList());
    }
}
