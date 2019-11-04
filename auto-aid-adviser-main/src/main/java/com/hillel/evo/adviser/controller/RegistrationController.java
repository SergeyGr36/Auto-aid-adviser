package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.dto.AdviserUserDetailsDto;
import com.hillel.evo.adviser.dto.UserRegistrationDto;
import com.hillel.evo.adviser.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private transient final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public AdviserUserDetailsDto userDetails(@RequestBody UserRegistrationDto user) {
        return userService.registration(user);
    }

    @GetMapping("/activation/{code}")
    public AdviserUserDetailsDto userActivation(@PathVariable String code) {
        return userService.activation(code);
    }
}
