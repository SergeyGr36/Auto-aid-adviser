package com.hillel.evo.adviser.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class BusinessDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private LocationDto location;

    private ContactDto contact;
    private String workingDays;
    private String workingHours;

    @NotEmpty
    private List<ServiceBusinessShortDto> serviceBusinesses;

    @NotNull
    private Long userId;
}
