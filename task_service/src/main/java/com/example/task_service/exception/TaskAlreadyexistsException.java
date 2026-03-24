package com.example.task_service.exception;

public class TaskAlreadyexistsException extends RuntimeException{

    public TaskAlreadyexistsException(){}

    public TaskAlreadyexistsException(String message){
        super(message);
    }
}
