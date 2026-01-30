package com.legenkiy.note_api.controllers.advice;


import com.legenkiy.note_api.dto.ErrorValidationApi;
import com.legenkiy.note_api.exceptions.ObjectNotFoundException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<?> handleObjectNotFoundException() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorValidationApi> handleIllegalArgument() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleConflict() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorValidationApi> handlerConstraintViolationException(MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest httpServletRequest) {
        Map<String, String> erroredFields = new HashMap<>();
        methodArgumentNotValidException.getFieldErrors().forEach(
                errors -> {
                    String key = errors.getField();
                    String value = errors.getDefaultMessage();
                    erroredFields.put(key, value);

                }
        );
        ErrorValidationApi errorApi = ErrorValidationApi.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation error")
                .path(httpServletRequest.getRequestURI())
                .fieldErrors(erroredFields)
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorApi);
    }
}
