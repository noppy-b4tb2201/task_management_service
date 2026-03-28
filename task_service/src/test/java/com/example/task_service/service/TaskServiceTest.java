package com.example.task_service.service;

import com.example.task_service.dto.request.TaskCreateRequestDto;
import com.example.task_service.dto.request.TaskUpdateRequestDto;
import com.example.task_service.dto.response.TaskResponseDto;
import com.example.task_service.entity.Task;
import com.example.task_service.enums.Status;
import com.example.task_service.exception.TaskAlreadyexistsException;
import com.example.task_service.exception.TaskNotFoundException;
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

import java.util.List;
import java.util.Optional;
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

    @Test
    @DisplayName("Task Already Exists")
    void create_Failed() {
        //Arrange
        UUID userId = UUID.randomUUID();
        TaskCreateRequestDto request = new TaskCreateRequestDto();
        request.setTitle("Title");
        request.setDescription("Description");

        when(taskRepository.existsByUserIdAndTitle(userId, "Title")).thenReturn(true);

        //Act&Assert
        assertThrows(TaskAlreadyexistsException.class, () -> {
            taskService.create(userId, request);
        });

        verify(taskRepository, times(0)).save(any());

    }

    @Test
    @DisplayName("Task Found")
    void findById_Success() {

        UUID userId = UUID.randomUUID();
        Long id = 1L;

        Task task = new Task();
        task.setUserId(userId);
        task.setId(id);

        when(taskRepository.findByUserIdAndId(userId, id)).thenReturn(Optional.of(task));
        TaskResponseDto response = taskService.findById(task.getUserId(), task.getId());

        assertNotNull(response);

        verify(taskRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Task Not Found")
    void findbyId_Failed() {

        UUID userId = UUID.randomUUID();
        Long id = 1L;

        Task task = new Task();
        task.setUserId(userId);
        task.setId(id);

        when(taskRepository.findByUserIdAndId(userId, id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.findById(task.getUserId(), task.getId());
        });

        verify(taskRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Task Found")
    void findByUserId_Success() {
        UUID userId = UUID.randomUUID();

        Task task = new Task();
        task.setUserId(userId);

        when(taskRepository.findByUserId(userId)).thenReturn(List.of(task));

        List<TaskResponseDto> response = taskService.findByUserId(task.getUserId());

        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Test
    @DisplayName("Task Not Found")
    void findByUserId_Failed() {
        UUID userId = UUID.randomUUID();

        Task task = new Task();
        task.setUserId(userId);

        when(taskRepository.findByUserId(userId)).thenReturn(List.of());

        List<TaskResponseDto> response = taskService.findByUserId(task.getUserId());

        assertEquals(0, response.size());
        verify(taskRepository, times(0)).save(any());

    }

    @Test
    @DisplayName("Task Updated")
    void updateTaskByUserIdAndId_Success() {
        UUID userId = UUID.randomUUID();
        Long id = 1L;

        Task task = new Task();
        task.setUserId(userId);
        task.setId(id);

        TaskUpdateRequestDto request = new TaskUpdateRequestDto();
        request.setStatus(Status.DONE);
        request.setDescription("description");

        when(taskRepository.findByUserIdAndId(userId, id)).thenReturn(Optional.of(task));

        TaskResponseDto response = taskService.updateTaskByUserIdAndId(userId, id, request);

        assertNotNull(response);
        assertEquals(response.getStatus(), Status.DONE);
        assertEquals(response.getDescription(), "description");
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Task Not Updated")
    void updateTaskByUserIdAndId_Failed() {
        UUID userId = UUID.randomUUID();
        Long id = 1L;

        Task task = new Task();
        task.setUserId(userId);
        task.setId(id);

        TaskUpdateRequestDto request = new TaskUpdateRequestDto();
        request.setStatus(Status.DONE);
        request.setDescription("description");

        when(taskRepository.findByUserIdAndId(userId, id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTaskByUserIdAndId(userId, id, request);
        });

        verify(taskRepository, times(0)).save(any());

    }

}
