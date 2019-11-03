package com.hillel.evo.adviser.business.dto;

import com.hillel.evo.adviser.business.entity.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class BusinessDTO {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private Location localization;
    @NonNull
    private Contact contact;
    @NonNull
    private String workingDays;
    @NonNull
    private String workingHours;
    @NonNull
    private TypeBusiness businessType;
    @NonNull
    private BusinessUser businessUser;
    @NonNull
    private List<ServiceBusiness> serviceBusinesses;

}
