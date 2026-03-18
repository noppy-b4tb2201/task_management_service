package com.example.task_service.controller;

import com.example.task_service.dto.request.TaskCreateRequestDto;
import com.example.task_service.dto.response.CommonResponseDto;
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
    public CommonResponseDto<TaskResponseDto> add(@AuthenticationPrincipal UUID userId, @RequestBody @Valid TaskCreateRequestDto request) {
        TaskResponseDto taskResponseDto = taskService.create(userId, request);

        return CommonResponseDto.success(taskResponseDto, "task added successfully");
    }

    //タスク部分検索コントローラー
    @PostMapping(value = "/serach")
    @ResponseStatus(HttpStatus.FOUND)
    public CommonResponseDto<TaskResponseDto> search(@AuthenticationPrincipal UUID userId, Long id) {
        TaskResponseDto taskResponseDto = taskService.findById(userId, id);

        return CommonResponseDto.success(taskResponseDto, "Task Found");
    }


}
