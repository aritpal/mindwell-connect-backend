package com.aritpal.mindwell_connect.service;

import com.aritpal.mindwell_connect.dto.RegisterRequest;
import com.aritpal.mindwell_connect.entity.Role;
import com.aritpal.mindwell_connect.entity.User;
import com.aritpal.mindwell_connect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterRequest registerRequest) {

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use.");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode((registerRequest.getPassword())));
        user.setRole(Role.valueOf(registerRequest.getRole().toUpperCase()));

        userRepository.save(user);

    }

}
