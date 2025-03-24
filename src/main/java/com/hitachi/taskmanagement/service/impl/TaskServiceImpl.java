package com.hitachi.taskmanagement.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.hitachi.taskmanagement.model.Task;
import com.hitachi.taskmanagement.model.Project;
import com.hitachi.taskmanagement.model.dto.request.TaskCreateRequest;
import com.hitachi.taskmanagement.model.dto.response.TaskResponse;
import com.hitachi.taskmanagement.repository.ProjectRepository;
import com.hitachi.taskmanagement.repository.TaskRepository;
import com.hitachi.taskmanagement.service.TaskService;

public class TaskServiceImpl implements TaskService {

  @Autowired
  private TaskRepository taskRepository;
  
  @Autowired
  private ProjectRepository projectRepository;

  @Override
  public TaskResponse createTask(TaskCreateRequest taskRequest) {
    Task task = new Task();
    task.setName(taskRequest.getName());
    task.setDescription(taskRequest.getDescription());
    
    Project project = projectRepository.findById(taskRequest.getProjectId())
        .orElseThrow(() -> new RuntimeException("Project not found"));
    task.setProject(project);
    
    task.setDueDate(taskRequest.getDueDate());
    return convertToTaskResponse(task);
  }

  @Override
  public TaskResponse updateTask(Long id, TaskCreateRequest taskRequest) {
    Task task = taskRepository.findById(id).orElseThrow(() ->new RuntimeException("Task not found"));
    task.setName(taskRequest.getName());
    task.setDescription(taskRequest.getDescription());
    task.setDueDate(taskRequest.getDueDate());
    return convertToTaskResponse(task);
  }

  @Override
  public TaskResponse getTaskById(Long id) {
    Task task = taskRepository.findById(id).orElseThrow(() ->new RuntimeException("Task not found"));
    return convertToTaskResponse(task);
  }

  @Override
  public List<TaskResponse> getAllTasks(int page, int size) {
    Pageable pageable = PageRequest.of(page,size);
    Page<Task> tasks = taskRepository.findAll(pageable);
    List<TaskResponse> taskResponses = tasks.stream().map(this::convertToTaskResponse).collect(Collectors.toList());
    return taskResponses;
  }

  @Override
  public void deleteTask(Long id) {
    taskRepository.deleteById(id);
  }

  private TaskResponse convertToTaskResponse(Task task)  {
    TaskResponse taskResponse = new TaskResponse();
    taskResponse.setId(task.getId());
    taskResponse.setName(task.getName());
    taskResponse.setDescription(task.getDescription());
    taskResponse.setPriority(task.getPriority());
    taskResponse.setStatus(task.getStatus());
    taskResponse.setDueDate(task.getDueDate());
    taskResponse.setProjectName(task.getProject().getName());
    taskResponse.setCreatedByUser(task.getCreatedBy().getUsername());
    taskResponse.setCreatedAt(task.getCreatedAt());
    taskResponse.setUpdatedAt(task.getUpdatedAt());
    return taskResponse;
  }
}
