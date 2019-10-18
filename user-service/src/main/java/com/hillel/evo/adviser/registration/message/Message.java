package com.hillel.evo.adviser.registration.message;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Message {
    USER_NOT_FOUND("User %s not found"),
    USER_ALREADY_EXISTS("User with mail %s already exists"),

    ACTIVE_CODE_NOT_FOUND("User with activation code not found");

    private String discript;

    public String getDiscript() {
        return discript;
    }
}
