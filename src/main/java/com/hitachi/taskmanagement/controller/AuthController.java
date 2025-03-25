package com.hitachi.taskmanagement.controller;

import com.hitachi.taskmanagement.model.RefreshToken;
import com.hitachi.taskmanagement.model.Role;
import com.hitachi.taskmanagement.model.User;
import com.hitachi.taskmanagement.model.enums.RoleType;
import com.hitachi.taskmanagement.repository.RoleRepository;
import com.hitachi.taskmanagement.repository.UserRepository;
import com.hitachi.taskmanagement.security.services.UserDetailsImpl;
import com.hitachi.taskmanagement.service.JwtService;
import com.hitachi.taskmanagement.service.RefreshTokenService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.hitachi.taskmanagement.model.dto.request.LoginRequest;
import com.hitachi.taskmanagement.model.dto.request.SignUpRequest;
import com.hitachi.taskmanagement.model.dto.request.TokenRefreshRequest;
import com.hitachi.taskmanagement.model.dto.response.JwtResponse;
import com.hitachi.taskmanagement.model.dto.response.TokenRefreshResponse;
import com.hitachi.taskmanagement.exception.TokenRefreshException;      

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(
                accessToken,
                refreshToken.getToken(),
                "Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList())));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration).map(RefreshToken::getUser).map(user -> {
            String token = jwtService.generateToken(UserDetailsImpl.build(user));
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
            return ResponseEntity.ok(new TokenRefreshResponse(token, refreshToken.getToken(), "Bearer"));

        }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }
    
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() { 
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetailsImpl.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok("Logout successfully!");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setFullName(signUpRequest.getFirstName() + " " + signUpRequest.getLastName());

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "manager":
                        Role managerRole = roleRepository.findByName(RoleType.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(managerRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Hello World");
    }
}