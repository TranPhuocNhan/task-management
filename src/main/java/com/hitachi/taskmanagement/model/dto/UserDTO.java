package com.hitachi.taskmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String role;
    @JsonIgnore
    private String password;
} 