package com.hitachi.taskmanagement.model.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Data
public class ProjectRequest {
    @NotBlank(message = "Project name is required")
    private String name;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotNull(message = "Manager ID is required")
    private Long managerId;

    @NotNull(message = "Created by is required")
    private String createdBy;
    
    private Set<Long> teamMemberIds;
    
    private String status;
    
    private String startDate;
    
    private String endDate;
} 