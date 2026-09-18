package com.monil.taskflow.service;

import com.monil.taskflow.dto.*;
import com.monil.taskflow.exception.ResourceNotFoundException;
import com.monil.taskflow.model.Project;
import com.monil.taskflow.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projects;
    public ProjectService(ProjectRepository projects) { this.projects = projects; }

    @Transactional
    public ProjectResponse create(ProjectRequest request) {
        return ProjectResponse.from(projects.save(new Project(request.name().trim(), request.description())));
    }
    @Transactional(readOnly = true)
    public List<ProjectResponse> list() { return projects.findAll().stream().map(ProjectResponse::from).toList(); }
    @Transactional(readOnly = true)
    public Project require(Long id) {
        return projects.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project " + id + " does not exist"));
    }
}
