package com.shylesh.urlshortener.exception;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUrlNotFound(UrlNotFoundException ex) {

    ErrorResponse error = new ErrorResponse(ex.getMessage(), new Date());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
}

    public record ErrorResponse(String message, Date timestamp) {

    }
}
