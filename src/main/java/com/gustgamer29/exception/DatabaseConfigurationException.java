package com.gustgamer29.exception;

public class DatabaseConfigurationException extends AbstractException {

    public DatabaseConfigurationException(String message) {
        super(message);
    }

    public DatabaseConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
