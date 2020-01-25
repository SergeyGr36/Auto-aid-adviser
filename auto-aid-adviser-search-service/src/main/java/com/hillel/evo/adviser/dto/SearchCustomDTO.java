package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.lucene.search.Query;

import java.util.List;
import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public class SearchCustomDTO {

    private Class<?> clazz;
    List<Supplier<Query>> queries;
}
