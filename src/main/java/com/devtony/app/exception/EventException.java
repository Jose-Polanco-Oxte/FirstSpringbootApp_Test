package com.devtony.app.exception;

public class EventException extends AbstractException {
  public EventException(String message, ExceptionDetails details, Throwable e) {
    super(message, details, e);
  }

  public EventException(String message, ExceptionDetails details) {
    super(message, details);
  }
}
