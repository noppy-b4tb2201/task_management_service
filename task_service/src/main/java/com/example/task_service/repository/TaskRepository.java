package com.example.task_service.repository;

import com.example.task_service.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserId(UUID userId);

    Optional<Task> findByUserIdAndId(UUID userId, Long id);

    boolean existsByUserIdAndTitle(UUID userId, String title);
}
