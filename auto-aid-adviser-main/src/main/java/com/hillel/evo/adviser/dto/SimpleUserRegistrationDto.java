package com.hillel.evo.adviser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class SimpleUserRegistrationDto {
    @Email
    @NotNull
    private String email;

    @Min(8)
    @NotNull
    private String password;
}
