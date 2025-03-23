package com.hitachi.taskmanagement.service;

import java.util.List;

import com.hitachi.taskmanagement.model.dto.request.ProjectRequest;
import com.hitachi.taskmanagement.model.dto.response.ProjectResponse;

public interface ProjectService {
  ProjectResponse createProject(ProjectRequest projectRequest);
  ProjectResponse updateProject(Long id, ProjectRequest projectRequest);
  ProjectResponse getProjectById(Long id);
  List<ProjectResponse> getAllProjects();
  void deleteProject(Long id);
}
