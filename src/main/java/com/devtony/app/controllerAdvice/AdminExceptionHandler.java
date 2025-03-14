package com.devtony.app.controllerAdvice;

import com.devtony.app.exception.AdminException;
import com.devtony.app.exception.ExceptionsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(50)
@ControllerAdvice
public class AdminExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AdminExceptionHandler.class);

    @ExceptionHandler(value = {AdminException.class})
    public ResponseEntity<ExceptionsResponse> handleAdminException(AdminException exception) {
        LOG.error(exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getDetails().getResponse(), HttpStatus.UNAUTHORIZED);
    }
}
