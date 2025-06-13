package com.aritpal.mindwell_connect.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientProfileRequest {
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phone;
    private String concerns;
}
