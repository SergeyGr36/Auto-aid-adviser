package com.hillel.evo.adviser.handler;

import com.hillel.evo.adviser.exception.AccessTokenExpiredException;
import com.hillel.evo.adviser.exception.InvalidJwtTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class AdviserExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<String> malformedParameterHandler(Exception e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Constraint violation");
    }

    @ExceptionHandler(AccessTokenExpiredException.class)
    protected ResponseEntity<String> expiredTokenHandler(Exception e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired");
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    protected ResponseEntity<String> invalidTokenHandler(Exception e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
}
