package com.anik.product_inventory.exception;

import com.anik.product_inventory.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidSkuFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSkuFormatException(InvalidSkuFormatException e) {

        log.warn("Invalid SKU format: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException e) {

        log.warn("Product not found: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(e.getLocalizedMessage(),HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(SkuAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleSkuAlreadyExistsException(SkuAlreadyExistsException e) {

        log.warn("SKU already exists: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(e.getLocalizedMessage(),HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException e) {

        log.error("Validation failed: {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
