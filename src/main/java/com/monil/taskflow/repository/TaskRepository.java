package com.monil.taskflow.repository;

import com.monil.taskflow.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectIdOrderByCreatedAtDesc(Long projectId);
    List<Task> findAllByOrderByCreatedAtDesc();
}
