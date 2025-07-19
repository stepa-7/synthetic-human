package com.stepa7.starter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse error = new ErrorResponse(
                "INTERNAL_ERROR",
                ex.getMessage(), path,
                404,
                LocalDateTime.now().toString());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        return ResponseEntity.badRequest().body(new ErrorResponse(
                "INTERNAL_ERROR",
                ex.getMessage(), path,
                404,
                LocalDateTime.now().toString()));
    }

    @ExceptionHandler(NoAvailableAndroidException.class)
    public ResponseEntity<ErrorResponse> handleNoAndroids(NoAvailableAndroidException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse error = new ErrorResponse(
                "NO_AVAILABLE_ANDROID",
                ex.getMessage(), path,
                503,
                LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(CommandQueueOverflowException.class)
    public ResponseEntity<ErrorResponse> handleQueueOverflow(CommandQueueOverflowException ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorResponse error = new ErrorResponse(
                "QUEUE_OVERFLOW",
                ex.getMessage(),
                path,
                503,
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }
}