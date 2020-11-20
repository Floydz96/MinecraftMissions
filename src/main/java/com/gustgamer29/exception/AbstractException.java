package com.gustgamer29.exception;

public class AbstractException extends Exception {

    public AbstractException(String message) {
        super(message);
    }

    public AbstractException(String message, Throwable cause) {
        super(message, cause);
    }
}
