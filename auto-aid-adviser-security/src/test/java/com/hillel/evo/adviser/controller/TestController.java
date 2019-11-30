package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.BaseTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/unsecured")
    public ResponseEntity<String> helloUnsecured() {
        return ResponseEntity.status(OK).body("Hello from UNsecured controller");
    }


    @Secured("ROLE_USER")
    @GetMapping("/secured")
    public ResponseEntity<String> helloSecured() {
        return ResponseEntity.status(OK).body("Hello from Secured controller");
    }
}
