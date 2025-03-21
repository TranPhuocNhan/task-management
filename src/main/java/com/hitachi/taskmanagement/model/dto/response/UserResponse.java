package com.hitachi.taskmanagement.model.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private Set<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}