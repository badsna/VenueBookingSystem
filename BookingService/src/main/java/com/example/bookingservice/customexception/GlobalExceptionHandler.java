package com.example.bookingservice.customexception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(VenueNotAvailableException.class)
    public ResponseEntity<Map<String,Object>> handlerVenueNotAvailableException(VenueNotAvailableException venueNotAvailableException) {
        Map<String,Object> map=new HashMap<>();
        map.put("message", venueNotAvailableException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(VenueNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handlerVenueNotFoundException(VenueNotFoundException venueNotFoundException) {
        Map<String,Object> map=new HashMap<>();
        map.put("message", venueNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(DateMisMatchedException.class)
    public ResponseEntity<Map<String,Object>> handleDateMisMatchedException(DateMisMatchedException dateMisMatchedException) {
        Map<String,Object> map=new HashMap<>();
        map.put("message", dateMisMatchedException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handlerResourceNotFoundException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String,String > errorMap=new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errorMap);
    }
}
