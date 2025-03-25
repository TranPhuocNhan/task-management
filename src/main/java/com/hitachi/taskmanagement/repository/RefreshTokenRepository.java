package com.hitachi.taskmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.hitachi.taskmanagement.model.RefreshToken;
import com.hitachi.taskmanagement.model.User;

import jakarta.transaction.Transactional;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Transactional
    int deleteByUser(User user);
}
