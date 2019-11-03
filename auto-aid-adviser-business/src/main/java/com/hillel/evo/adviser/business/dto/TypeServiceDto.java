package com.hillel.evo.adviser.business.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TypeServiceDto {
    private long id;
    private String type;
    private TypeBusinessDto typeBusiness;
}
