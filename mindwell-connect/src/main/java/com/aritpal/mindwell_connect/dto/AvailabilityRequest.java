package com.aritpal.mindwell_connect.dto;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Data
public class AvailabilityRequest {

    private DayOfWeek dayOfWeek;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
