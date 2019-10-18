package com.hillel.evo.adviser.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/unsecured")
    public String helloUnsecured() {
        return "Hello from UNsecured controller";
    }

    @GetMapping("/secured")
    @Secured("ROLE_USER")
    public String helloSecured() {
        return "Hello from Secured controller";
    }
}
