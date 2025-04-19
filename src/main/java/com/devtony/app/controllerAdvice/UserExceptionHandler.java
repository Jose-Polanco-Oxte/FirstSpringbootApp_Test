package com.devtony.app.controllerAdvice;

import com.devtony.app.exception.ExceptionsResponse;
import com.devtony.app.exception.UserAuthException;
import com.devtony.app.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@Order(1)
@ControllerAdvice
public class UserExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(UserExceptionHandler.class);

    @ExceptionHandler(value = {UserException.class})
    public ResponseEntity<ExceptionsResponse> handleUserException(UserException exception) {
        LOG.error(exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getDetails().getResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException exception){
        LOG.error(exception.getMessage(), exception);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Argumentos no válidos");
        response.put("severity", "low");
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()){
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        response.put("errors", fieldErrors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserAuthException.class})
    public ResponseEntity<ExceptionsResponse> handleUserAuthExceptions(UserAuthException exception) {
        LOG.error(exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getDetails().getResponse(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {AuthorizationDeniedException.class})
    public ResponseEntity<ExceptionsResponse> handleAuthorizationDeniedException(AuthorizationDeniedException exception) {
        LOG.error(exception.getMessage(), exception);
        var details = new ExceptionsResponse("No tienes permisos para realizar esta acción", "high");
        return new ResponseEntity<>(details, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ExceptionsResponse> handleBadCredentialsException(BadCredentialsException exception) {
        LOG.error(exception.getMessage(), exception);
        var details = new ExceptionsResponse("Credenciales inválidas", "medium");
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
}
