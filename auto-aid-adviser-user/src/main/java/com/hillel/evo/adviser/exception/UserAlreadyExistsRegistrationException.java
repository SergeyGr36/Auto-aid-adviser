package com.hillel.evo.adviser.exception;

public class UserAlreadyExistsRegistrationException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public UserAlreadyExistsRegistrationException() {
    }

    public UserAlreadyExistsRegistrationException(String message) {
        super(message);
    }
}
