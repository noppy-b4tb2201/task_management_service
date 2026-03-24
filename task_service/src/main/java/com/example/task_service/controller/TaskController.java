package com.example.task_service.controller;

import com.example.task_service.dto.request.TaskCreateRequestDto;
import com.example.task_service.dto.request.TaskUpdateRequestDto;
import com.example.task_service.dto.response.CommonResponseDto;
import com.example.task_service.dto.response.TaskResponseDto;
import com.example.task_service.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @GetMapping(value = "/search/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto<TaskResponseDto> search(@AuthenticationPrincipal UUID userId, @PathVariable Long id) {
        TaskResponseDto taskResponseDto = taskService.findById(userId, id);

        return CommonResponseDto.success(taskResponseDto, "Task Found");
    }

    //タスク全件検索コントローラー
    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto<List<TaskResponseDto>> searchAll(@AuthenticationPrincipal UUID userId) {
        List<TaskResponseDto> taskResponseDto = taskService.findByUserId(userId);

        return CommonResponseDto.success(taskResponseDto, "Task Found");
    }

    //タスク更新コントローラー
    @PutMapping(value = "/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto<TaskResponseDto> update(@AuthenticationPrincipal UUID userId, @PathVariable Long id, @RequestBody @Valid TaskUpdateRequestDto request) {
        TaskResponseDto response = taskService.updateTaskByUserIdAndId(userId, id, request);

        return CommonResponseDto.success(response, "Task Updated");
    }

    //タスク削除コントローラー
    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto<Void> delete(@AuthenticationPrincipal UUID userId, @PathVariable Long id) {
        TaskResponseDto response = taskService.delete(userId, id);

        return CommonResponseDto.success(null, "Task Deleted");
    }
}
