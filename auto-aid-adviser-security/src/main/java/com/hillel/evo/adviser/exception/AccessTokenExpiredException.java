package com.hillel.evo.adviser.exception;

public class AccessTokenExpiredException extends RuntimeException {
    public AccessTokenExpiredException() {
    }

    public AccessTokenExpiredException(String message) {
        super(message);
    }

    public AccessTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessTokenExpiredException(Throwable cause) {
        super(cause);
    }
}
