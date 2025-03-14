package com.devtony.app.exception;

public abstract class AbstractException extends RuntimeException {
    private ExceptionDetails details;

    public AbstractException(String message, ExceptionDetails details, Throwable e) {
        super(message, e);
        this.details = details;
    }

    public AbstractException(String message, ExceptionDetails details) {
        super(message);
        this.details = details;
    }

    public ExceptionDetails getDetails() {
        return details;
    }

    public void setDetails(ExceptionDetails details) {
        this.details = details;
    }
}
