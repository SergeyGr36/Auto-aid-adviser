package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchTextDTO extends SearchInputDTO {

    private Class<?> clazz;
    private String field;
    private String param;
}
