package com.hitachi.taskmanagement.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;
}
