package com.monil.taskflow.controller;

import com.monil.taskflow.dto.*;
import com.monil.taskflow.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService service;
    public ProjectController(ProjectService service) { this.service = service; }

    @PostMapping
    ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectRequest request) {
        ProjectResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/v1/projects/" + created.id())).body(created);
    }
    @GetMapping
    List<ProjectResponse> list() { return service.list(); }
}
