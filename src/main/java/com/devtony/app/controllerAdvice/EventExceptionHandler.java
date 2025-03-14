package com.devtony.app.controllerAdvice;

import com.devtony.app.exception.EventException;
import com.devtony.app.exception.ExceptionsResponse;
import com.devtony.app.exception.InvitationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(100)
@ControllerAdvice
public class EventExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EventExceptionHandler.class);

    @ExceptionHandler(value = {EventException.class})
    public ResponseEntity<ExceptionsResponse> handleEventException(EventException exception) {
        LOG.error(exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getDetails().getResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InvitationException.class})
    public ResponseEntity<ExceptionsResponse> handleInvitationException(InvitationException exception) {
        LOG.error(exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getDetails().getResponse(), HttpStatus.BAD_REQUEST);
    }
}
