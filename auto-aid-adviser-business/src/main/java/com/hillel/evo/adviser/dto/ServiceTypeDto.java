package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTypeDto {
    private long id;
    private String name;
    private BusinessTypeDto businessType;

    public ServiceTypeDto(String name, BusinessTypeDto businessType) {
        this.name = name;
        this.businessType = businessType;
    }
}
