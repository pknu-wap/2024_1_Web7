package com.dogventure.dogweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionController {

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<String> illegalAccessHandler(IllegalAccessException e) {

        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
}