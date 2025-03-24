package com.hitachi.taskmanagement.repository;

import com.hitachi.taskmanagement.model.Task;
import com.hitachi.taskmanagement.model.User;
import com.hitachi.taskmanagement.model.Project;
import com.hitachi.taskmanagement.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProject(Project project);

    List<Task> findByCreatedBy(User createdBy);
    List<Task> findByProjectAndCreatedBy(Project project, User createdBy);
    List<Task> findByProjectAndStatus(Project project, TaskStatus status);
    
}
