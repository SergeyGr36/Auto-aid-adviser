package com.hillel.evo.adviser.registration.controller;

import com.hillel.evo.adviser.registration.dto.RegistrationDto;
import com.hillel.evo.adviser.registration.dto.UserDto;
import com.hillel.evo.adviser.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/activation/{code}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDto activation(@PathVariable String code) {
        return userService.activation(code);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> registration(@Validated @RequestBody RegistrationDto dto) {
        return userService.registration(dto);
    }
}
