package com.aritpal.mindwell_connect.service;

import com.aritpal.mindwell_connect.dto.RegisterRequest;
import com.aritpal.mindwell_connect.dto.UserDto;
import com.aritpal.mindwell_connect.entity.Role;
import com.aritpal.mindwell_connect.entity.User;
import com.aritpal.mindwell_connect.repository.UserRepository;
import com.aritpal.mindwell_connect.util.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use.");
        }

        if (request.getRole() == null) {
            throw new RuntimeException("Role must not be null.");
        }

        if (Role.ADMIN.equals(request.getRole())) {
            throw new RuntimeException("Registration as ADMIN is not allowed.");
        }

        User user = User.builder().email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(request.getRole()).isActive(true).build();

        userRepository.save(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void updateUserRole(Long id, Role newRole) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(newRole);
    }

    @Transactional
    public void updateUserStatus(Long id, boolean active) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsActive(active);
    }
}
