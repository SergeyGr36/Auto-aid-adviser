package com.hillel.evo.adviser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UpdateResourceException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public UpdateResourceException() {
        super();
    }

    public UpdateResourceException(String message) {
        super(message);
    }
}
