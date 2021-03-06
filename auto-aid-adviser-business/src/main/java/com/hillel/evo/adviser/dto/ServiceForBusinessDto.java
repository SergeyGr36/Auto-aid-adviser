package com.hillel.evo.adviser.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceForBusinessDto {
    private Long id;
    private String name;
    private ServiceTypeDto serviceType;
}
