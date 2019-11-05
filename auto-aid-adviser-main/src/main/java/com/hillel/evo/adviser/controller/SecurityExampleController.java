package com.hillel.evo.adviser.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/example")
public class SecurityExampleController {

    /**
     * This endpoint could be accessed without jwt token or with valid jwt token.
     * When accessed with invalid jwt token, the response status is Unauthorized 401;
     *
     * @return
     */
    @GetMapping("/unsecured")
    public ResponseEntity<String> helloUnsecured() {
        return ResponseEntity.status(OK).body("Hello from UNsecured controller");
    }

    /**
     * This endpoint could be accessed only with valid jwt token.
     * When accessed with invalid jwt token, the response status is Unauthorized 401;
     * When accessed with valid jwt token, but with wrong role, the response status is Forbidden 403;
     *
     * @return
     */
    @Secured("ROLE_USER")
    @GetMapping("/secured")
    public ResponseEntity<String> helloSecured() {
        return ResponseEntity.status(OK).body("Hello from Secured controller");
    }
}
