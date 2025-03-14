package com.devtony.app.controllerAdvice;

import com.devtony.app.exception.ExceptionsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Order(100)
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseEntity<ExceptionsResponse> handleGeneralException(Throwable exception) {
        LOG.error(exception.getMessage(), exception);
        var details =  new ExceptionsResponse("Error inesperado", "hight");
        return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ExceptionsResponse> handleHttpReqNotSupport(Throwable exception) {
        LOG.error(exception.getMessage(), exception);
        var details = new ExceptionsResponse("Método no válido para este end point", "medium");
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NoResourceFoundException.class})
    public ResponseEntity<ExceptionsResponse> handleNoResourceFound(Throwable exception) {
        LOG.error(exception.getMessage(), exception);
        var details = new ExceptionsResponse("Endpoint no encontrado", "low");
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }
}
