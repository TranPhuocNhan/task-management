package com.hitachi.taskmanagement.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hitachi.taskmanagement.model.dto.response.ErrorResponse;

@ControllerAdvice
public class GlobalExcetionHandler {
    @ExceptionHandler(TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleTokenRefreshException(TokenRefreshException exception)
    {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}
