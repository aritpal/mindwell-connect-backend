package com.aritpal.mindwell_connect.dto;

import com.aritpal.mindwell_connect.entity.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponse {
    private Long id;
    private String therapistName;
    private String patientEmail;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AppointmentStatus status;
}
