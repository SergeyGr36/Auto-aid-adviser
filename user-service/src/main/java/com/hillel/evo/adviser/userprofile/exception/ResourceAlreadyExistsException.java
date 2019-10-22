package com.hillel.evo.adviser.userprofile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class ResourceAlreadyExistsException extends RuntimeException {
    static final long serialVersionUID = 1L;
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
