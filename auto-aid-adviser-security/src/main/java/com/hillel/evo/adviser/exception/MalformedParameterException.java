package com.hillel.evo.adviser.exception;

public class MalformedParameterException extends RuntimeException{

    public MalformedParameterException() {
    }

    public MalformedParameterException(String message) {
        super(message);
    }

    public MalformedParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public MalformedParameterException(Throwable cause) {
        super(cause);
    }
}
