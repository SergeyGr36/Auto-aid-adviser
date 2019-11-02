package com.hillel.evo.adviser.handler;

import com.hillel.evo.adviser.exception.AccessTokenExpiredException;
import com.hillel.evo.adviser.exception.InvalidJwtTokenException;
import com.hillel.evo.adviser.exception.MalformedParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviserExceptionHandler {

    @ExceptionHandler(MalformedParameterException.class)
    protected ResponseEntity<String> malformedParameterHandler(Exception e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Malformed parameter :" + e.getMessage());
    }

    @ExceptionHandler(AccessTokenExpiredException.class)
    protected ResponseEntity<String> expiredTokenHandler(Exception e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired :" + e.getMessage());
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    protected ResponseEntity<String> malformedParameter(Exception e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token :" + e.getMessage());
    }
}
