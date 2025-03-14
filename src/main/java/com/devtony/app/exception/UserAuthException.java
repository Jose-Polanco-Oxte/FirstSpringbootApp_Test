package com.devtony.app.exception;

public class UserAuthException extends AbstractException{

    public UserAuthException(String message, ExceptionDetails details, Throwable e) {
        super(message, details, e);
    }

    public UserAuthException(String message, ExceptionDetails details) {
        super(message, details);
    }
}
