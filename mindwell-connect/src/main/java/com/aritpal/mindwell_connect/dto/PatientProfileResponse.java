package com.aritpal.mindwell_connect.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PatientProfileResponse {
    private Long id;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phone;
    private String concerns;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
