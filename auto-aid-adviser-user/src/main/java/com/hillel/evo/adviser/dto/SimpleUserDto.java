package com.hillel.evo.adviser.dto;

import com.hillel.evo.adviser.entity.AdviserUserDetails;
import lombok.AllArgsConstructor;
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

    public SimpleUserDto(Long id, @NotNull String firstName, @NotNull String lastName, @NotNull String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
}

