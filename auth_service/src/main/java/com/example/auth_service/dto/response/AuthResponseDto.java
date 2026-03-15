package com.example.auth_service.dto.response;

import lombok.Data;

@Data
public class AuthResponseDto {

    private String accessToken;

    private String tokenType;
}