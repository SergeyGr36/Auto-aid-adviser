package com.hillel.evo.adviser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//todo прописать exception
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DeleteException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public DeleteException() {
    }
    public DeleteException(String message) {
        super(message);
    }
}
