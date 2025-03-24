package com.hitachi.taskmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hitachi.taskmanagement.model.Role;
import com.hitachi.taskmanagement.model.User;
import com.hitachi.taskmanagement.model.Project;
import com.hitachi.taskmanagement.model.Task;
import com.hitachi.taskmanagement.model.enums.RoleType;
import com.hitachi.taskmanagement.model.enums.ProjectStatus;
import com.hitachi.taskmanagement.model.enums.TaskPriority;
import com.hitachi.taskmanagement.model.enums.TaskStatus;
import com.hitachi.taskmanagement.repository.RoleRepository;
import com.hitachi.taskmanagement.repository.UserRepository;
import com.hitachi.taskmanagement.repository.ProjectRepository;
import com.hitachi.taskmanagement.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Thêm các role mặc định nếu chưa tồn tại
        for (RoleType roleType : RoleType.values()) {
            if (!roleRepository.existsByName(roleType)) {
                Role role = new Role();
                role.setName(roleType);
                roleRepository.save(role);
            }
        }

        // Tạo admin user nếu chưa tồn tại
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("Admin User");

            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));
            roles.add(adminRole);
            admin.setRoles(roles);

            userRepository.save(admin);
        }

        // Tạo manager user nếu chưa tồn tại
        if (!userRepository.existsByUsername("manager")) {
            User manager = new User();
            manager.setUsername("manager");
            manager.setEmail("manager@example.com");
            manager.setPassword(passwordEncoder.encode("manager123"));
            manager.setFullName("Manager User");

            Set<Role> roles = new HashSet<>();
            Role managerRole = roleRepository.findByName(RoleType.ROLE_MANAGER)
                .orElseThrow(() -> new RuntimeException("Manager role not found"));
            roles.add(managerRole);
            manager.setRoles(roles);

            userRepository.save(manager);
        }

        // Tạo user thường nếu chưa tồn tại
        if (!userRepository.existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setFullName("Normal User");

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User role not found"));
            roles.add(userRole);
            user.setRoles(roles);

            userRepository.save(user);
        }

        // Tạo dữ liệu mẫu cho projects và tasks
        User admin = userRepository.findByUsername("admin")
            .orElseThrow(() -> new RuntimeException("Admin user not found"));
        User manager = userRepository.findByUsername("manager")
            .orElseThrow(() -> new RuntimeException("Manager user not found"));
        User user = userRepository.findByUsername("user")
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Tạo project mẫu
        if (!projectRepository.existsByName("Project A")) {
            Project projectA = new Project();
            projectA.setName("Project A");
            projectA.setDescription("This is a sample project");
            projectA.setStartDate(LocalDateTime.now());
            projectA.setEndDate(LocalDateTime.now().plusMonths(3));
            projectA.setStatus(ProjectStatus.PLANNING);
            projectA.setCreatedBy(admin);
            
            Set<User> teamMembers = new HashSet<>();
            teamMembers.add(manager);
            teamMembers.add(user);
            projectA.setTeamMembers(teamMembers);
            
            projectRepository.save(projectA);

            // Tạo tasks cho project A
            Task task1 = new Task();
            task1.setName("Task 1");
            task1.setDescription("Implement user authentication");
            task1.setPriority(TaskPriority.HIGH);
            task1.setStatus(TaskStatus.IN_PROGRESS);
            task1.setDueDate(LocalDateTime.now().plusDays(7));
            task1.setProject(projectA);
            task1.setCreatedBy(admin);
            task1.setAssignedTo(user);
            taskRepository.save(task1);

            Task task2 = new Task();
            task2.setName("Task 2");
            task2.setDescription("Design database schema");
            task2.setPriority(TaskPriority.MEDIUM);
            task2.setStatus(TaskStatus.TODO);
            task2.setDueDate(LocalDateTime.now().plusDays(14));
            task2.setProject(projectA);
            task2.setCreatedBy(admin);
            task2.setAssignedTo(manager);
            taskRepository.save(task2);
        }

        // Tạo project mẫu thứ 2
        if (!projectRepository.existsByName("Project B")) {
            Project projectB = new Project();
            projectB.setName("Project B");
            projectB.setDescription("Another sample project");
            projectB.setStartDate(LocalDateTime.now().plusDays(7));
            projectB.setEndDate(LocalDateTime.now().plusMonths(2));
            projectB.setStatus(ProjectStatus.PLANNING);
            projectB.setCreatedBy(manager);
            
            Set<User> teamMembers = new HashSet<>();
            teamMembers.add(user);
            projectB.setTeamMembers(teamMembers);
            
            projectRepository.save(projectB);

            // Tạo task cho project B
            Task task3 = new Task();
            task3.setName("Task 3");
            task3.setDescription("Create project documentation");
            task3.setPriority(TaskPriority.LOW);
            task3.setStatus(TaskStatus.TODO);
            task3.setDueDate(LocalDateTime.now().plusDays(10));
            task3.setProject(projectB);
            task3.setCreatedBy(manager);
            task3.setAssignedTo(user);
            taskRepository.save(task3);
        }
    }
} 