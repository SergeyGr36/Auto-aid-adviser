package com.hillel.evo.adviser.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BusinessDto {
    private Long id;
    private String name;
    private LocationDto location;
    private ContactDto contact;
    private String workingDays;
    private String workingHours;
    private List<Long> serviceBusinessesIds;
    private Long userId;
}
