package com.devtony.app.exception;

public class AdminException extends AbstractException {

    public AdminException(String message, ExceptionDetails details) {
        super(message, details);
    }

    public AdminException(String message, ExceptionDetails details, Throwable e) {
        super(message, details, e);
    }
}
