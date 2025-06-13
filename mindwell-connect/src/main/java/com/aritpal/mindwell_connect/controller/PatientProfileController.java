package com.aritpal.mindwell_connect.controller;

import com.aritpal.mindwell_connect.dto.PatientProfileRequest;
import com.aritpal.mindwell_connect.dto.PatientProfileResponse;
import com.aritpal.mindwell_connect.service.PatientProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient/profile")
@PreAuthorize("hasRole('PATIENT')")
public class PatientProfileController {

    @Autowired
    private PatientProfileService patientProfileService;

    @PostMapping
    public ResponseEntity<PatientProfileResponse> saveOrUpdateProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody PatientProfileRequest request) {
        return ResponseEntity.ok(patientProfileService.createOrUpdate(userDetails.getUsername(), request));
    }

    @GetMapping
    public ResponseEntity<PatientProfileResponse> getOwnProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(patientProfileService.getOwnProfile(userDetails.getUsername()));
    }
}
