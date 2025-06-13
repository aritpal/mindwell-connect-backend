package com.aritpal.mindwell_connect.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TherapistProfileResponse {

    private Long id;
    private String fullName;
    private String specialties;
    private String qualifications;
    private String bio;
    private Double consultationFee;
    private UserDto user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
