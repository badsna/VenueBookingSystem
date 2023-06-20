package com.example.apigateway.customexception;


import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Map<String,Object>> handlerLoginException(LoginException loginException) {
        Map<String,Object> map=new HashMap<>();
        map.put("message", loginException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
    }

    @ExceptionHandler(RoleNotMatchedException.class)
    public ResponseEntity<Map<String,Object>> handlerRoleNotMatchedException(RoleNotMatchedException roleNotMatchedException) {
        Map<String,Object> map=new HashMap<>();
        map.put("message", roleNotMatchedException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
    }


}
