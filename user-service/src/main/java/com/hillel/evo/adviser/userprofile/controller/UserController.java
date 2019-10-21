package com.hillel.evo.adviser.userprofile.controller;

import com.hillel.evo.adviser.userprofile.service.SimpleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final SimpleUserService userService;

    @Autowired
    public UserController(SimpleUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/activation/{code}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void activation(@PathVariable String code) {

    }
}
