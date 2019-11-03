package com.hillel.evo.adviser.business.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceBusinessDto {
    private Long id;
    private String type;
    private String typeService;
}
