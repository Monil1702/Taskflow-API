package com.monil.taskflow.dto;

import com.monil.taskflow.model.Priority;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record TaskRequest(
    @NotBlank @Size(max = 160) String title,
    @Size(max = 1000) String description,
    @NotNull Priority priority,
    @Email @Size(max = 120) String assigneeEmail,
    @FutureOrPresent LocalDate dueDate,
    @NotNull @Positive Long projectId
) {}
