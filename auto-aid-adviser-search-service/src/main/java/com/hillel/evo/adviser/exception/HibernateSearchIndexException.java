package com.hillel.evo.adviser.exception;

public class HibernateSearchIndexException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public HibernateSearchIndexException(String message) {
        super(message);
    }

    public HibernateSearchIndexException(String message, Throwable cause) {
        super(message, cause);
    }
}
