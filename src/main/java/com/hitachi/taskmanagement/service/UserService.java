package com.hitachi.taskmanagement.service;

import com.hitachi.taskmanagement.model.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    List<UserDTO> getAllUsers();
    void deleteUser(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
