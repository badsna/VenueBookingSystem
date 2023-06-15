package com.example.venueservice.customexception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> handlerResourceAlreadyExistsException(ResourceAlreadyExistsException resourceAlreadyExistsException) {
        Map<String,Object> map=new HashMap<>();
        map.put("message", resourceAlreadyExistsException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handlerResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        Map<String,Object> map=new HashMap<>();
        map.put("message", resourceNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String,Object>> handlerAccessDeniedException(AccessDeniedException accessDeniedException) {
        Map<String,Object> map=new HashMap<>();
        map.put("message", accessDeniedException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
    }
}
