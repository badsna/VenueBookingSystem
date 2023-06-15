package com.example.userservice.loginForm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class LoginResponseDto {
    private String token;
}
