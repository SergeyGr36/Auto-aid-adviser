package com.hillel.evo.adviser.dto;

import com.hillel.evo.adviser.enums.RoleUser;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationDto {
    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 8)
    private String password;

    @NotEmpty
    private RoleUser role;
}
