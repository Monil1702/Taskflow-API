package com.monil.taskflow.controller;

import com.monil.taskflow.dto.*;
import com.monil.taskflow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService service;
    public TaskController(TaskService service) { this.service = service; }

    @PostMapping
    ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request) {
        TaskResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/tasks/" + created.id())).body(created);
    }
    @GetMapping
    List<TaskResponse> list(@RequestParam(required = false) Long projectId) { return service.list(projectId); }
    @PatchMapping("/{id}/status")
    TaskResponse updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest request) {
        return service.updateStatus(id, request);
    }
}
