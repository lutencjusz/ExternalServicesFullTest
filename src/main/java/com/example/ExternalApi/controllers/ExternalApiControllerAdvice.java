package com.example.ExternalApi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExternalApiControllerAdvice {
    @ExceptionHandler(MissingServletRequestParameterException.class)

    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Brak wymaganego parametru: " + name);
    }
}
