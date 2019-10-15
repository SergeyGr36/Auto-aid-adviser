package com.hillel.evo.adviser.registration.dto;

import com.hillel.evo.adviser.registration.enums.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationDto {
    private String mail;
    private String password;
    private RoleUser role;
}
