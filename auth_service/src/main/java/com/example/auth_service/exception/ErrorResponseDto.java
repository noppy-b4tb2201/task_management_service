package com.example.auth_service.exception;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ErrorResponseDto {

    //エラー発生時間
    private LocalDateTime timestamp;

    //HTTPステータス
    private int status;

    //エラーメッセージ
    private String message;

    //エラー発生API
    private String path;
}
