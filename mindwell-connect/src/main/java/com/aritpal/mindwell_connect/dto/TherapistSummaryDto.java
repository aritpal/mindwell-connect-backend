package com.aritpal.mindwell_connect.dto;

import lombok.Data;

@Data
public class TherapistSummaryDto {

    private Long id;
    private String fullName;
    private String specialties;
    private Double consultationFee;

}
