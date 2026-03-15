package com.example.task_service.entity;

import com.example.task_service.dto.request.TaskUpdateRequestDto;
import com.example.task_service.dto.response.TaskResponseDto;
import com.example.task_service.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 250)
    private String description;

    //デフォルトではステータスは"TODO"
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.TODO;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void create() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void update() {
        updatedAt = LocalDateTime.now();
    }

    //デフォルトコンストラクタ
    public Task(){

    }

    //タスク更新用メソッド
    public void updateTask(TaskUpdateRequestDto request) {

        this.description = request.getDescription();
        this.status = request.getStatus();
    }
}
