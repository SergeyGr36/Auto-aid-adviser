package com.hillel.evo.adviser.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceBusinessDto {
    private Long id;
    private String type;
    private ServiceTypeDto typeService;
}
