package com.example.task_service.service;

import com.example.task_service.dto.request.TaskCreateRequestDto;
import com.example.task_service.dto.response.TaskResponseDto;
import com.example.task_service.entity.Task;
import com.example.task_service.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("Task Craeted Successfully")
    void create_Success() {
        //Arrange
        UUID userId = UUID.randomUUID();
        TaskCreateRequestDto request = new TaskCreateRequestDto();
        request.setTitle("Title");
        request.setDescription("Description");

        when(taskRepository.existsByUserIdAndTitle(any(), any())).thenReturn(false);

        //Act
        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle(request.getTitle());
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponseDto result = taskService.create(userId, request);

        //Assert
        assertNotNull(result);
        assertEquals("Title", result.getTitle());
        verify(taskRepository, times(1)).save(any());
    }
}
