package com.example.task_service.service;

import com.example.task_service.dto.request.TaskCreateRequestDto;
import com.example.task_service.dto.request.TaskUpdateRequestDto;
import com.example.task_service.dto.response.TaskResponseDto;
import com.example.task_service.entity.Task;
import com.example.task_service.exception.TaskAlreadyexistException;
import com.example.task_service.exception.TaskNotFoundException;
import com.example.task_service.repository.TaskRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //タスク追加
    @Transactional
    public TaskResponseDto create(UUID userId, TaskCreateRequestDto request) {

        if (taskRepository.existByUserIdAndTitle(userId, request.getTitle())) {
            throw new TaskAlreadyexistException("Task Already exist");
        }

        Task task = new Task();
        task.setUserId(userId);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        Task savedTask = taskRepository.save(task);

        return new TaskResponseDto(savedTask);
    }

    //タスク部分検索
    @Transactional
    public TaskResponseDto findById(UUID userId, Long id) {
        return taskRepository.findByUserIdAndId(userId, id)
                .map(TaskResponseDto::new)//DTOに変換
                .orElseThrow(() -> new TaskNotFoundException("Task Not Found")); //例外投げる
    }

    //タスク全検索
    @Transactional(readOnly = true)
    public List<TaskResponseDto> findByUserId(UUID userId) {
        return taskRepository.findByUserId(userId)
                .stream()
                .map(TaskResponseDto::new)
                .toList();
    }

    //タスク更新
    @Transactional
    public void updateTaskByUserIdAndId(UUID userId, Long id, TaskUpdateRequestDto request) {

        Task willBeUpdateTask = taskRepository.findByUserIdAndId(userId, id)
                                .orElseThrow(() -> new TaskNotFoundException("Task Not Found"));

        willBeUpdateTask.updateTask(request);

    }

    //タスク削除
    @Transactional
    public void delete(UUID userId, Long id) {

        Task deletedTask = taskRepository.findByUserIdAndId(userId, id)
                           .orElseThrow(() -> new TaskNotFoundException("Task NOt Found"));

        taskRepository.delete(deletedTask);
    }
}
