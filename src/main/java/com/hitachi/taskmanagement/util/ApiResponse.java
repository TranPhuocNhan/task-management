package com.hitachi.taskmanagement.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) { 
        return new ApiResponse<T>(true, "Thành công", data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<T>(true, message, null);
    }
    
    
}
