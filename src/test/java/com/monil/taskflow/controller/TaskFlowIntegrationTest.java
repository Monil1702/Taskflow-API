package com.monil.taskflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monil.taskflow.repository.ProjectRepository;
import com.monil.taskflow.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "taskflow.api-key=test-key")
@AutoConfigureMockMvc
class TaskFlowIntegrationTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;
    @Autowired TaskRepository tasks;
    @Autowired ProjectRepository projects;

    @BeforeEach
    void cleanDatabase() { tasks.deleteAll(); projects.deleteAll(); }

    @Test
    void rejectsRequestsWithoutApiKey() throws Exception {
        mvc.perform(get("/api/v1/projects")).andExpect(status().isUnauthorized());
    }

    @Test
    void createsProjectAndTaskThenUpdatesStatus() throws Exception {
        String projectJson = mvc.perform(post("/api/v1/projects")
                .header("X-API-Key", "test-key").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Launch\",\"description\":\"Portfolio launch\"}"))
            .andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Launch"))
            .andReturn().getResponse().getContentAsString();
        long projectId = json.readTree(projectJson).get("id").asLong();

        String taskJson = mvc.perform(post("/api/v1/tasks")
                .header("X-API-Key", "test-key").contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Write README\",\"priority\":\"HIGH\",\"projectId\":" + projectId + "}"))
            .andExpect(status().isCreated()).andExpect(jsonPath("$.status").value("TODO"))
            .andReturn().getResponse().getContentAsString();
        long taskId = json.readTree(taskJson).get("id").asLong();

        mvc.perform(patch("/api/v1/tasks/{id}/status", taskId)
                .header("X-API-Key", "test-key").contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"DONE\"}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    void rejectsInvalidTaskBeforeServiceLayer() throws Exception {
        mvc.perform(post("/api/v1/tasks").header("X-API-Key", "test-key")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"\",\"priority\":\"HIGH\",\"projectId\":1}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Request validation failed"));
    }
}
