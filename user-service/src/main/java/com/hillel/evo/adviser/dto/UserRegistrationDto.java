package com.hillel.evo.adviser.dto;

import com.hillel.evo.adviser.enums.RoleUser;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationDto {

    @NotEmpty
    @Email(regexp = "^[\\w-\\.]+@[\\w-\\.]+$", message = "Violation of email format")
    @Size(min = 3, max = 128)
    private String email;

    @NotEmpty
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[\\w]{8,64}", message = "Violation of password format")
    private String password;

    @NotEmpty
    private RoleUser role;
}
