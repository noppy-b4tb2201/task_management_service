package com.example.task_service.exception;

public class TaskAlreadyexistException extends RuntimeException{

    public TaskAlreadyexistException(){}

    public TaskAlreadyexistException(String message){
        super(message);
    }
}
