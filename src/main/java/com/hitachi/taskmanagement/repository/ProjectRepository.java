package com.hitachi.taskmanagement.repository;

import com.hitachi.taskmanagement.model.Project;
import com.hitachi.taskmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByTeamMembersContaining(User teamMember);
  
    boolean existsByName(String name);
}
