package com.hillel.evo.adviser.security.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LoginRequestDto {

    @Email()
    @NotBlank()
    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="Invalid email format")
    @Size(min = 6, max = 40)
    private String email;

    @NotBlank()
    @Pattern(regexp="[A-Za-z0-9_-]+", message="Invalid password format")
    @Size(min = 8, max = 64)
    private String password;

}
