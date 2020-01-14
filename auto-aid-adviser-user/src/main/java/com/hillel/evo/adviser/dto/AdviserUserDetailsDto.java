package com.hillel.evo.adviser.dto;

import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.enums.RoleUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdviserUserDetailsDto {
    private Long id;
    private String email;
    private RoleUser role;

    public AdviserUserDetailsDto(AdviserUserDetails adviserUserDetails) {
        this.id = adviserUserDetails.getId();
        this.email = adviserUserDetails.getEmail();
        this.role = adviserUserDetails.getRole();
    }
}
