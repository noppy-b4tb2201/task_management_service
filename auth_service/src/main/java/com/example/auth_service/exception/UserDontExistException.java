package com.example.auth_service.exception;

public class UserDontExistException extends RuntimeException{

    public UserDontExistException() {
    }

    public UserDontExistException(String message) {
        super(message);
    }
}
