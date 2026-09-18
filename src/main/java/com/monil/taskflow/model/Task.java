package com.monil.taskflow.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "tasks", indexes = @Index(name = "idx_task_project_status", columnList = "project_id,status"))
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 160)
    private String title;
    @Column(length = 1000)
    private String description;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private TaskStatus status;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Priority priority;
    @Column(length = 120)
    private String assigneeEmail;
    private LocalDate dueDate;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private Instant updatedAt;

    protected Task() {}
    public Task(String title, String description, Priority priority, String assigneeEmail, LocalDate dueDate, Project project) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.assigneeEmail = assigneeEmail;
        this.dueDate = dueDate;
        this.project = project;
        this.status = TaskStatus.TODO;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }
    public void changeStatus(TaskStatus status) { this.status = status; this.updatedAt = Instant.now(); }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public Priority getPriority() { return priority; }
    public String getAssigneeEmail() { return assigneeEmail; }
    public LocalDate getDueDate() { return dueDate; }
    public Project getProject() { return project; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
