package com.hillel.evo.adviser.handler;

import com.hillel.evo.adviser.com.hillel.evo.adviser.message.ErrorMessage;
import com.hillel.evo.adviser.exception.ActivationCodeFoundNoMatchException;
import com.hillel.evo.adviser.exception.UserAlreadyExistsRegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

import static com.hillel.evo.adviser.com.hillel.evo.adviser.message.ErrorMessage.CONSTRAINT_VIOLATION;
import static com.hillel.evo.adviser.com.hillel.evo.adviser.message.ErrorMessage.INVALID_CODE;
import static com.hillel.evo.adviser.com.hillel.evo.adviser.message.ErrorMessage.USER_ALREADY_EXISTS;

@ControllerAdvice
public class AdviserExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<String> malformedParameterHandler(Exception e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CONSTRAINT_VIOLATION.getMsg());
    }

    @ExceptionHandler(ActivationCodeFoundNoMatchException.class)
    protected ResponseEntity<String> invalidCodeHandler(Exception e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_CODE.getMsg());
    }

    @ExceptionHandler(UserAlreadyExistsRegistrationException.class)
    protected ResponseEntity<String> userAlreadyExistsHandler(Exception e) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(USER_ALREADY_EXISTS.getMsg());
    }


}
