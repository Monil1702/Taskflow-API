package com.monil.taskflow.repository;

import com.monil.taskflow.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {}
