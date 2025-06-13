package com.aritpal.mindwell_connect.dto;

import com.aritpal.mindwell_connect.entity.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;
    private String email;
    private Role role;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
