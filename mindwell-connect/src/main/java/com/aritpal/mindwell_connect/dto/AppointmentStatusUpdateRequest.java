package com.aritpal.mindwell_connect.dto;

import com.aritpal.mindwell_connect.entity.AppointmentStatus;
import lombok.Data;

@Data
public class AppointmentStatusUpdateRequest {
    private AppointmentStatus status;
}
