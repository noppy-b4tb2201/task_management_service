package com.example.auth_service.controller;

import com.example.auth_service.dto.request.LoginRequestDto;
import com.example.auth_service.dto.request.RegisterRequestDto;
import com.example.auth_service.dto.response.AuthResponseDto;
import com.example.auth_service.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public String register(@RequestBody RegisterRequestDto request) {
        authService.register(request);
        return "User registered successfully";
    }

    @PostMapping(value = "/auth/login")
    public AuthResponseDto login(@RequestBody LoginRequestDto request){
        String token = authService.login(request);

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(token);
        authResponseDto.setTokenType("Bearer");

        return authResponseDto;
    }
}
