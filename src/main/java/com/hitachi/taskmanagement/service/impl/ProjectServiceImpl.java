package com.hitachi.taskmanagement.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.hitachi.taskmanagement.model.Project;
import com.hitachi.taskmanagement.model.dto.request.ProjectRequest;
import com.hitachi.taskmanagement.model.dto.response.ProjectResponse;
import com.hitachi.taskmanagement.model.enums.ProjectStatus;
import com.hitachi.taskmanagement.repository.ProjectRepository;
import com.hitachi.taskmanagement.service.ProjectService;
import com.hitachi.taskmanagement.service.UserService;

public class ProjectServiceImpl implements ProjectService {

  @Autowired
  private ProjectRepository projectRepository;

  @Override
  public ProjectResponse createProject(ProjectRequest projectRequest) {
    Project project = new Project();
    project.setName(projectRequest.getName());
    project.setDescription(projectRequest.getDescription());
    project.setStatus(ProjectStatus.valueOf(projectRequest.getStatus()));
    project.setStartDate(LocalDateTime.parse(projectRequest.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    project.setEndDate(
        LocalDateTime.parse(projectRequest.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    
    return convertToProjectResponse(project);
  }

  @Override
  public ProjectResponse updateProject(Long id, ProjectRequest projectRequest) {
    Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    project.setName(projectRequest.getName());
    project.setDescription(projectRequest.getDescription());
    project.setStatus(ProjectStatus.valueOf(projectRequest.getStatus()));
    project.setStartDate(LocalDateTime.parse(projectRequest.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    project.setEndDate(LocalDateTime.parse(projectRequest.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    return convertToProjectResponse(project);
  }

  @Override
  public ProjectResponse getProjectById(Long id) {
    Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    return convertToProjectResponse(project);
  }

  @Override
  public List<ProjectResponse> getAllProjects() {
    List<Project> projects = projectRepository.findAll();
    return projects.stream().map(this::convertToProjectResponse).collect(Collectors.toList());
  }

  @Override
  public void deleteProject(Long id) {
    projectRepository.deleteById(id);
  }
  
  private ProjectResponse convertToProjectResponse(Project project) {
    ProjectResponse projectResponse = new ProjectResponse();
    projectResponse.setId(project.getId());
    projectResponse.setName(project.getName());
    projectResponse.setDescription(project.getDescription());
    projectResponse.setStatus(project.getStatus());
    projectResponse.setCreatedBy(project.getCreatedBy().getFullName());
    projectResponse.setCreatedAt(project.getCreatedAt());
    projectResponse.setUpdatedAt(project.getUpdatedAt());
    return projectResponse;
  }
}