package com.hillel.evo.adviser.business.dto;

import com.hillel.evo.adviser.business.entity.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    private TypeBusinessDto typeBusiness;
    private List<ServiceBusinessDto> serviceBusinesses;

}
