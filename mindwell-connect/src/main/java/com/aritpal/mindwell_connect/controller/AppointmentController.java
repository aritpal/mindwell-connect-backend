package com.aritpal.mindwell_connect.controller;

import com.aritpal.mindwell_connect.dto.AppointmentRequest;
import com.aritpal.mindwell_connect.dto.AppointmentResponse;
import com.aritpal.mindwell_connect.dto.AppointmentStatusUpdateRequest;
import com.aritpal.mindwell_connect.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/book")
    public ResponseEntity<AppointmentResponse> bookAppointment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AppointmentRequest request) {
        AppointmentResponse response = appointmentService.bookAppointment(userDetails.getUsername(), request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/mine")
    public ResponseEntity<List<AppointmentResponse>> getPatientAppointments(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(appointmentService.getPatientAppointments(userDetails.getUsername()));
    }

    @PreAuthorize("hasRole('THERAPIST')")
    @GetMapping("/therapist")
    public ResponseEntity<List<AppointmentResponse>> getTherapistAppointments(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(appointmentService.getTherapistAppointments(userDetails.getUsername()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AppointmentResponse> updateStatus(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, @RequestBody AppointmentStatusUpdateRequest request) {
        return ResponseEntity.ok(appointmentService.updateStatus(id, userDetails.getUsername(), request.getStatus()));
    }

}
