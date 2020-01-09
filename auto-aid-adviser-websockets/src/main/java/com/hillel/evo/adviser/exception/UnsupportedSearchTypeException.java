package com.hillel.evo.adviser.exception;

public class UnsupportedSearchTypeException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public UnsupportedSearchTypeException(String message) {
        super(message);
    }

    public UnsupportedSearchTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
