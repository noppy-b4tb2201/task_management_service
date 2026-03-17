package com.example.task_service.controller;

import com.example.task_service.dto.request.TaskCreateRequestDto;
import com.example.task_service.dto.response.TaskResponseDto;
import com.example.task_service.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //タスク追加コントローラー
    @PostMapping(value = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto add(@AuthenticationPrincipal UUID userId, @RequestBody @Valid TaskCreateRequestDto request) {
        return taskService.create(userId, request);
    }
}
