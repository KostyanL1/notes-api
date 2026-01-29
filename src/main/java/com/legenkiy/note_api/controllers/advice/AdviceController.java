package com.legenkiy.note_api.controllers.advice;


import io.jsonwebtoken.JwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.ObjectStreamException;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(ObjectStreamException.class)
    public ResponseEntity<?> handleObjectNotFoundException(){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND).build();
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().build();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument() {
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

}
