package com.hillel.evo.adviser.exception;

public class S3ServiceValidationException extends RuntimeException{
    static final long serialVersionUID = 1L;

    public S3ServiceValidationException(String message) {
        super(message);
    }

}
