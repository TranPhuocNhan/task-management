package com.hitachi.taskmanagement.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProjectDTO {
  private Long id;
  private String name;
  private String description;
  private String status;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  
}
