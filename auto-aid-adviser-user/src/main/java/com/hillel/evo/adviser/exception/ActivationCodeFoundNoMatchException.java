package com.hillel.evo.adviser.exception;

public class ActivationCodeFoundNoMatchException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public ActivationCodeFoundNoMatchException() {
    }

    public ActivationCodeFoundNoMatchException(String message) {
        super(message);
    }
}
