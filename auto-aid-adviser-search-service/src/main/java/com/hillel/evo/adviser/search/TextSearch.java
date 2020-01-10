package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.dto.SearchTextDTO;

import java.util.List;

public interface TextSearch<T> {

    List<T> search(final SearchTextDTO dto);
    List<T> searchWildcard(final SearchTextDTO dto);
}
