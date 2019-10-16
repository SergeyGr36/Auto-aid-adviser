package com.hillel.evo.adviser.registration.dto;

import com.hillel.evo.adviser.registration.enums.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class RegistrationDto {
    @Email
    private String mail;
    @Min(4)
    private String password;
    private RoleUser role;
}
