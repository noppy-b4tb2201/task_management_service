package com.example.auth_service.exception;

public class UserAlreadyExistsException extends RuntimeException{

    //引数なしコンストラクタ
    public UserAlreadyExistsException() {
    }

    //メッセージ引数ありコンストラクタ
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
