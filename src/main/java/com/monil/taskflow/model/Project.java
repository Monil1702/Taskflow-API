package com.monil.taskflow.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "projects")
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(length = 500)
    private String description;
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected Project() {}
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.createdAt = Instant.now();
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Instant getCreatedAt() { return createdAt; }
}
