package com.hitachi.taskmanagement.repository;

import com.hitachi.taskmanagement.model.Task;
import com.hitachi.taskmanagement.model.User;
import com.hitachi.taskmanagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProject(Project project);

    List<Task> findByAssignedTo(User assignedTo);
    List<Task> findByProjectAndAssignedTo(Project project, User assignedTo);
    List<Task> findByProjectAndStatus(Project project, String status);
    
}
