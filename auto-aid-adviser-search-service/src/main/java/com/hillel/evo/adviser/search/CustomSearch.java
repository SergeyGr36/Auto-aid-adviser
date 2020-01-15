package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.dto.SearchCustomDTO;
import com.hillel.evo.adviser.mapper.BaseMapper;

import java.util.List;

public interface CustomSearch<T> {

    List<T> search(final BaseMapper mapper, final SearchCustomDTO dto);
}
