package com.example.userservice.loginForm.controller;

import com.example.userservice.loginForm.dto.LoginRequestDto;
import com.example.userservice.loginForm.dto.LoginResponseDto;
import com.example.userservice.loginForm.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    @PostMapping("/authenticate")
    public LoginResponseDto authenticate(@RequestBody LoginRequestDto loginRequestDto){
        return loginService.authenticateRequest(loginRequestDto);

    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        loginService.validateToken(token);
        return "Token is valid";
    }
}
