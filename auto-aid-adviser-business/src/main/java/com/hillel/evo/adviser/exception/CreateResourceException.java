package com.hillel.evo.adviser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CreateResourceException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public CreateResourceException() {
        super();
    }

    public CreateResourceException(String message) {
        super(message);
    }
}
