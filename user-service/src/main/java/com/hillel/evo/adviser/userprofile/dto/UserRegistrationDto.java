package com.hillel.evo.adviser.userprofile.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public abstract class UserRegistrationDto {
    @Email
    @NotNull
    private String email;

    @Min(8)
    @NotNull
    private String password;
}
