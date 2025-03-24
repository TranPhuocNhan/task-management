package com.hitachi.taskmanagement.model.dto.response;
import com.hitachi.taskmanagement.model.enums.TaskPriority;
import com.hitachi.taskmanagement.model.enums.TaskStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskResponse {
    private Long id;
    private String name;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDateTime dueDate;
    private String projectName;
    private String assignedToUser;
    private String createdByUser;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}