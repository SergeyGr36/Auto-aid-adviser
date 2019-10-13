package com.hillel.evo.adviser.registration.dtos;

import com.hillel.evo.adviser.registration.enums.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private boolean active;
    private String mail;
    private RoleUser role;
}
