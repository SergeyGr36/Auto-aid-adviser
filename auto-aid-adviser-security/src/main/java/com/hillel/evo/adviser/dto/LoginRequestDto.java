package com.hillel.evo.adviser.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LoginRequestDto {

    @Email(regexp = "^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*$", message = "Invalid email format")
    @Size(min = 6, max = 40)
    private String email;

    @Pattern(regexp="[\\w-]+", message="Invalid password format")
    @Size(min = 8, max = 64)
    private String password;
}
