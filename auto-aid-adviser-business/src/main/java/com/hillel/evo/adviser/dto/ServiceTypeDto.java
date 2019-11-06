package com.hillel.evo.adviser.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceTypeDto {
    private long id;
    private String type;
    private BusinessTypeDto typeBusiness;
}
