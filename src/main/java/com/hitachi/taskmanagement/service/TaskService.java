package com.hitachi.taskmanagement.service;

import java.util.List;

import com.hitachi.taskmanagement.model.dto.request.TaskCreateRequest;
import com.hitachi.taskmanagement.model.dto.response.TaskResponse;

public interface TaskService   {
  TaskResponse createTask(TaskCreateRequest taskRequest);
  TaskResponse updateTask(Long id, TaskCreateRequest taskRequest);
  TaskResponse getTaskById(Long id);
  List<TaskResponse> getAllTasks();
  void deleteTask(Long id);
}
