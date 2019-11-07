package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTypeDto {
    private long id;
    private String type;
    private BusinessTypeDto businessType;
}
