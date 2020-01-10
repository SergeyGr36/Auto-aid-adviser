package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.dto.SearchSpatialDTO;
import com.hillel.evo.adviser.mapper.BaseMapper;

import java.util.List;

public interface SpatialSearch<T> {

    List<T> search(final BaseMapper mapper, final SearchSpatialDTO dto);
}
