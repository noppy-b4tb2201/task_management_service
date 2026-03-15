package com.example.task_service.dto.request;

import com.example.task_service.enums.Status;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskUpdateRequestDto {

    @Size(max = 250)
    private String description;

    private Status status;
}
