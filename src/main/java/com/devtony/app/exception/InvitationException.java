package com.devtony.app.exception;

public class InvitationException extends AbstractException {
    public InvitationException(String message, ExceptionDetails details, Throwable e) {
        super(message, details, e);
    }

    public InvitationException(String message, ExceptionDetails details) {
        super(message, details);
    }
}
