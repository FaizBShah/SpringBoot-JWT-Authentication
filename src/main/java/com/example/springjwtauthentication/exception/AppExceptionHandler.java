package com.example.springjwtauthentication.exception;

import com.example.springjwtauthentication.model.response.ErrorResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponseMessage> appException(AppException exception, WebRequest request) {
        ErrorResponseMessage errorMessage = new ErrorResponseMessage(false, exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(errorMessage);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseMessage> usernameNotFoundException(UsernameNotFoundException exception, WebRequest request) {
        ErrorResponseMessage errorMessage = new ErrorResponseMessage(false, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseMessage> badCredentialsException(BadCredentialsException exception, WebRequest request) {
        ErrorResponseMessage errorMessage = new ErrorResponseMessage(false, "Email/Password does not match");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
