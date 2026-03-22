package com.example.auth_service.dto.response;

import lombok.Data;

@Data
public class CommonResponseDto<T> {

    private String message;

    private boolean success;

    private T responseData;

    public static <T> CommonResponseDto<T> success(T responseData, String message) {

        CommonResponseDto<T> response = new CommonResponseDto<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setResponseData(responseData);

        return response;
    }

    public static <T> CommonResponseDto<T> error(String message) {

        CommonResponseDto<T> response = new CommonResponseDto<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setResponseData(null);

        return response;
    }
}
