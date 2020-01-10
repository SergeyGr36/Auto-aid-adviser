package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FacetDTO extends SearchInputDTO {

    private Class<?> clazz;
    private String name;
    private String field;
    private List<Object> ranges;
}
