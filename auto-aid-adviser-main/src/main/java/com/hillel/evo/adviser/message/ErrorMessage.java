package com.hillel.evo.adviser.message;

public enum ErrorMessage{

    CONSTRAINT_VIOLATION("Constraint violation"),
    USER_ALREADY_EXISTS("Such user already exists"),
    INVALID_CODE("Activation code is invalid");

    private final String message;

    private ErrorMessage(String message) {
        this.message = message;
    }

    public String getMsg() {
        return message;
    }
}
