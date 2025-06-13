package com.aritpal.mindwell_connect.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private Long therapistId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
