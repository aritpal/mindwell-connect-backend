package com.aritpal.mindwell_connect.dto;

import lombok.Data;

@Data
public class TherapistProfileRequest {

    private String fullName;
    private String specialties;
    private String qualifications;
    private String bio;
    private Double consultationFee;

}
