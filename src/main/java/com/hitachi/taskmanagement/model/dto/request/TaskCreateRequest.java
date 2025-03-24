package com.hitachi.taskmanagement.model.dto.request;

import java.time.LocalDateTime;

import com.hitachi.taskmanagement.model.enums.TaskPriority;
import com.hitachi.taskmanagement.model.enums.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskCreateRequest {
    @NotBlank(message = "Task name is required")
    private String name;

    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message = "Priority is required")
    private TaskPriority priority;

    @NotNull(message = "Due date is required")
    private String dueDate;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private Long assignedToUserId;
    
}
