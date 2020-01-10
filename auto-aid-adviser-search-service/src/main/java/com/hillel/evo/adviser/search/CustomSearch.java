package com.hillel.evo.adviser.search;

import com.hillel.evo.adviser.dto.SearchCustomDTO;

import java.util.List;

public interface CustomSearch<T> {

    List<T> search(final SearchCustomDTO dto);
}
