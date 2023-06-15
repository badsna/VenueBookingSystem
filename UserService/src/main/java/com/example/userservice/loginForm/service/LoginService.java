package com.example.userservice.loginForm.service;

import com.example.userservice.config.JwtService;
import com.example.userservice.loginForm.dto.LoginRequestDto;
import com.example.userservice.loginForm.dto.LoginResponseDto;
import com.example.userservice.repo.UserCredentialsRepo;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserCredentialsRepo userCredentialsRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public LoginResponseDto authenticateRequest(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        var user=userCredentialsRepo.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));

        var jwtToken=jwtService.generateToken(user);

        return LoginResponseDto.builder()
                .token(jwtToken)
                .build();

    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
