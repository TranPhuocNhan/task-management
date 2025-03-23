package com.hitachi.taskmanagement.service.impl;

import java.util.List;

import com.hitachi.taskmanagement.model.Task;
import com.hitachi.taskmanagement.model.dto.request.TaskCreateRequest;
import com.hitachi.taskmanagement.model.dto.response.TaskResponse;
import com.hitachi.taskmanagement.model.enums.TaskStatus;
import com.hitachi.taskmanagement.service.TaskService;

public class TaskServiceImpl implements TaskService {

  @Override
  public TaskResponse createTask(TaskCreateRequest taskRequest) {
    Task task = new Task();
    task.setName(taskRequest.getName());
    task.setDescription(taskRequest.getDescription());
    
    task.setProject(taskRequest.getProjectId());
    task.setAssignee(taskRequest.getAssigneeId());
    task.setDueDate(LocalDateTime.parse(taskRequest.getDueDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    return convertToTaskResponse(task);
  }

  @Override
  public TaskResponse updateTask(Long id, TaskCreateRequest taskRequest) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateTask'");
  }

  @Override
  public TaskResponse getTaskById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTaskById'");
  }

  @Override
  public List<TaskResponse> getAllTasks() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAllTasks'");
  }

  @Override
  public void deleteTask(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteTask'");
  }

}
