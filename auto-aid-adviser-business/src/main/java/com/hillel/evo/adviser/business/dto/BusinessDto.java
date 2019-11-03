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
    private Contact contact;
    private String workingDays;
    private String workingHours;
/*
    @NonNull
    private TypeBusiness businessType;
    @NonNull
    private BusinessUser businessUser;
*/

    private List<ServiceBusinessDto> serviceBusinesses;

}
