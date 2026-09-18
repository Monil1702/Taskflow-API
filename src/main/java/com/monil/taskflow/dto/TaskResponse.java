package com.monil.taskflow.dto;

import com.monil.taskflow.model.Priority;
import com.monil.taskflow.model.Task;
import com.monil.taskflow.model.TaskStatus;
import java.time.Instant;
import java.time.LocalDate;

public record TaskResponse(Long id, String title, String description, TaskStatus status, Priority priority,
                           String assigneeEmail, LocalDate dueDate, Long projectId, Instant createdAt, Instant updatedAt) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), task.getPriority(),
            task.getAssigneeEmail(), task.getDueDate(), task.getProject().getId(), task.getCreatedAt(), task.getUpdatedAt());
    }
}
