package com.hillel.evo.adviser.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TypeServiceDto {
    private long id;
    private String type;
    private TypeBusinessDto typeBusiness;
}
