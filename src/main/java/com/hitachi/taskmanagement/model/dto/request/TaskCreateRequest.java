package com.hitachi.taskmanagement.model.dto.request;

import java.time.LocalDateTime;

import com.hitachi.taskmanagement.model.enums.TaskPriority;
import com.hitachi.taskmanagement.model.enums.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskCreateRequest {
    @NotBlank(message = "Tên công việc không được để trống")
    private String title;

    @NotNull(message = "Độ ưu tiên không được để trống")
    private TaskPriority priority;

    @NotNull(message = "Ngày hết hạn không được để trống")
    private LocalDateTime dueDate;

    @NotNull(message = "ID dự án không được để trống")
    private Long projectId;

    private Long assignedToUserId;
    
}
