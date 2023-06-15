package com.example.userservice.customerexception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PasswordNotMatched.class)
    public ResponseEntity<Map<String,Object>> handlerResourceNotMatchedException(PasswordNotMatched passwordNotMatched) {
        Map<String,Object> map=new HashMap<>();
        map.put("password", passwordNotMatched.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(map);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> handlerUserAlreadyException(UserAlreadyExistsException userAlreadyExistsException) {
        Map<String,Object> map=new HashMap<>();
        map.put("password", userAlreadyExistsException.getMessage());
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
