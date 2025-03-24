package com.hitachi.taskmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.taskmanagement.model.dto.request.ProjectRequest;
import com.hitachi.taskmanagement.model.dto.response.ProjectResponse;
import com.hitachi.taskmanagement.service.ProjectService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

  @Autowired
  private ProjectService projectService;

  @PostMapping
  public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest projectRequest) {
    ProjectResponse projectResponse = projectService.createProject(projectRequest);
    return ResponseEntity.ok(projectResponse);
  }

  @GetMapping
  public ResponseEntity<List<ProjectResponse>> getAllProjects(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    List<ProjectResponse> projectResponses = projectService.getAllProjects(page, size);
    return ResponseEntity.ok(projectResponses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
    ProjectResponse projectResponse = projectService.getProjectById(id);
    return ResponseEntity.ok(projectResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest projectRequest) {
    ProjectResponse projectResponse = projectService.updateProject(id, projectRequest);
    return ResponseEntity.ok(projectResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
    projectService.deleteProject(id);
    return ResponseEntity.ok().build();
  }
}
