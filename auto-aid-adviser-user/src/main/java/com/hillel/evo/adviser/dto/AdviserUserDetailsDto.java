package com.hillel.evo.adviser.dto;

import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.enums.RoleUser;
import lombok.Data;

@Data
public class AdviserUserDetailsDto {
    private Long id;
    private String email;
    private RoleUser roleUser;

    public AdviserUserDetailsDto(AdviserUserDetails adviserUserDetails) {
        this.id = adviserUserDetails.getId();
        this.email = adviserUserDetails.getEmail();
        this.roleUser = adviserUserDetails.getRole();
    }
}
