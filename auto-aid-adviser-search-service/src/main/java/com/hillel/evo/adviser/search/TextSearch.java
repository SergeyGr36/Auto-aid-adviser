package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.dto.SearchTextDTO;
import com.hillel.evo.adviser.mapper.BaseMapper;

import java.util.List;

public interface TextSearch<T> {

    List<T> search(final BaseMapper mapper, final SearchTextDTO dto);
    List<T> searchWildcard(final BaseMapper mapper, final SearchTextDTO dto);
}
