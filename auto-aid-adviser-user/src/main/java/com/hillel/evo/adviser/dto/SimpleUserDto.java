package com.hillel.evo.adviser.dto;

import com.hillel.evo.adviser.entity.AdviserUserDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"id"})
public class SimpleUserDto {
    private Long id;
    @NotNull
    private AdviserUserDetails userDetails;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String phone;
}

