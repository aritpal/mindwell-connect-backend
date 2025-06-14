package com.aritpal.mindwell_connect.dto;

import com.aritpal.mindwell_connect.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private Role role;
}
