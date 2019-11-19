package com.hillel.evo.adviser.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class BusinessDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private LocationDto location;

    private ContactDto contact;

    private Set<WorkTimeDto> workTimes;

    @NotEmpty
    private List<ServiceForBusinessShortDto> serviceForBusinesses;

//    @NotNull
//    private Long userId;
}