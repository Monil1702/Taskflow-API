package com.monil.taskflow.dto;

import com.monil.taskflow.model.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequest(@NotNull TaskStatus status) {}
