package com.hitachi.taskmanagement.model.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import java.time.LocalDateTime;

@Data
public class ProjectCreateRequest {
    @NotBlank(message = "Tên dự án không được để trống")
    private String name;

    private String description;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDateTime startDate;

    @Future(message = "Ngày kết thúc phải là ngày tương lai")
    private LocalDateTime endDate;

}
