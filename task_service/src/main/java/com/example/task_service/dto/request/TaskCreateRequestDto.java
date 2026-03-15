package com.example.task_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskCreateRequestDto {

    @NotBlank
    @Size(max = 100)
    private String title;

    @Size(max = 250)
    private String description;
}
