package com.example.auth_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistsException (
         UserAlreadyExistsException ex,
         HttpServletRequest request) {

        ErrorResponseDto error = new ErrorResponseDto();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(409);
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserDontExistException.class)
    public ResponseEntity<ErrorResponseDto> handleUserDontExistsException (
            UserDontExistException ex,
            HttpServletRequest request) {

        ErrorResponseDto error = new ErrorResponseDto();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(404);
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidPasswordException (
            InvalidPasswordException ex,
            HttpServletRequest request) {

        ErrorResponseDto error = new ErrorResponseDto();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(401);
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralException (
            Exception ex,
            HttpServletRequest request) {

        ErrorResponseDto error = new ErrorResponseDto();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(500);
        error.setMessage("Internal server error");
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
