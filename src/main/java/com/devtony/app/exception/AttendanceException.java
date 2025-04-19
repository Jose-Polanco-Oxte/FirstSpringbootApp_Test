package com.devtony.app.exception;

public class AttendanceException extends AbstractException {

    public AttendanceException(String message, ExceptionDetails details, Throwable e) {
        super(message, details, e);
    }

    public AttendanceException(String message, ExceptionDetails details) {
        super(message, details);
    }
}
