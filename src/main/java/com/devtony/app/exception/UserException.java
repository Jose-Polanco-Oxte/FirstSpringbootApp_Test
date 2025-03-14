package com.devtony.app.exception;

public class UserException extends AbstractException {
    public UserException(String message, ExceptionDetails details, Throwable e) {
        super(message, details, e);
    }

    public UserException(String message, ExceptionDetails details) {
        super(message, details);
    }
}