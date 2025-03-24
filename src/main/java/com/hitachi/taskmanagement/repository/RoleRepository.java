package com.hitachi.taskmanagement.repository;

import com.hitachi.taskmanagement.model.Role;
import com.hitachi.taskmanagement.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
    boolean existsByName(RoleType name);
} 