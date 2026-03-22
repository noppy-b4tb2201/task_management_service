package com.example.task_service.exception;

import com.example.task_service.dto.response.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //サービス層からのエラー
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponseDto<Void> notFound(TaskNotFoundException e) {
        return CommonResponseDto.error(e.getMessage());
    }

    //サービス層からのエラー
    @ExceptionHandler(TaskAlreadyexistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CommonResponseDto<Void> alreadyExist(TaskAlreadyexistException e) {
        return CommonResponseDto.error(e.getMessage());
    }

    //DTOバリデーションエラー
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponseDto<Void> validationCheck(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                          .getAllErrors()
                          .get(0)
                          .getDefaultMessage();

        return CommonResponseDto.error(message);
    }
}
