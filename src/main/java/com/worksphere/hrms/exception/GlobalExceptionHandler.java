package com.worksphere.hrms.exception;

import com.worksphere.hrms.dto.response.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // =========================
    // RESOURCE NOT FOUND
    // =========================

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(
            ResourceNotFoundException ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        LocalDateTime.now(),
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND
        );
    }


    // =========================
    // DUPLICATE RESOURCE
    // =========================

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateResource(
            DuplicateResourceException ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        LocalDateTime.now(),
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CONFLICT
        );
    }


    // =========================
    // DATABASE CONSTRAINT
    // =========================

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(
                        false,
                        "Cannot delete or modify this resource because it is referenced by another record.",
                        LocalDateTime.now(),
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CONFLICT
        );
    }


    // =========================
    // INVALID REQUEST
    // =========================

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(
            IllegalArgumentException ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        LocalDateTime.now(),
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }


    // =========================
    // ACCESS DENIED
    // =========================

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(
            AccessDeniedException ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        LocalDateTime.now(),
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.FORBIDDEN
        );
    }


    // =========================
    // GENERAL EXCEPTION
    // =========================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(
            Exception ex) {

        ex.printStackTrace();

        ApiResponse<Object> response =
                new ApiResponse<>(
                        false,
                        "An unexpected error occurred.",
                        LocalDateTime.now(),
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}