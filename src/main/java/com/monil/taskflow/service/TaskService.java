package com.monil.taskflow.service;

import com.monil.taskflow.dto.*;
import com.monil.taskflow.exception.ResourceNotFoundException;
import com.monil.taskflow.model.Task;
import com.monil.taskflow.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository tasks;
    private final ProjectService projects;
    public TaskService(TaskRepository tasks, ProjectService projects) { this.tasks = tasks; this.projects = projects; }

    @Transactional
    public TaskResponse create(TaskRequest request) {
        Task task = new Task(request.title().trim(), request.description(), request.priority(), request.assigneeEmail(),
            request.dueDate(), projects.require(request.projectId()));
        return TaskResponse.from(tasks.save(task));
    }
    @Transactional(readOnly = true)
    public List<TaskResponse> list(Long projectId) {
        List<Task> result = projectId == null ? tasks.findAllByOrderByCreatedAtDesc() : tasks.findByProjectIdOrderByCreatedAtDesc(projectId);
        return result.stream().map(TaskResponse::from).toList();
    }
    @Transactional
    public TaskResponse updateStatus(Long id, StatusUpdateRequest request) {
        Task task = tasks.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task " + id + " does not exist"));
        task.changeStatus(request.status());
        return TaskResponse.from(task);
    }
}
