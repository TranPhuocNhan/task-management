package com.hitachi.taskmanagement.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.hitachi.taskmanagement.model.enums.ProjectStatus;

import lombok.Data;

@Data
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ProjectStatus status;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TaskResponse> tasks;
}
