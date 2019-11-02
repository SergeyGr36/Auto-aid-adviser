package com.hillel.evo.adviser;

import com.hillel.evo.adviser.exception.MalformedParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviserExceptionHandler {

    @ExceptionHandler(MalformedParameterException.class)
    protected ResponseEntity<String> malformedParameter(Exception e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Malformed parameter :" + e.getMessage());
    }
}
