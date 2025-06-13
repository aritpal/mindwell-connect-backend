package com.aritpal.mindwell_connect.controller;

import com.aritpal.mindwell_connect.dto.AvailabilityRequest;
import com.aritpal.mindwell_connect.dto.TherapistProfileRequest;
import com.aritpal.mindwell_connect.dto.TherapistProfileResponse;
import com.aritpal.mindwell_connect.entity.Availability;
import com.aritpal.mindwell_connect.service.TherapistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/therapist")
@PreAuthorize("hasRole('THERAPIST')")
public class TherapistController {

    @Autowired
    private TherapistService therapistService;

    @PostMapping("/profile")
    public ResponseEntity<TherapistProfileResponse> createProfile(@RequestBody TherapistProfileRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        TherapistProfileResponse profile = therapistService.createOrUpdateProfile(userDetails.getUsername(), request);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/profile")
    public ResponseEntity<TherapistProfileResponse> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        TherapistProfileResponse profile = therapistService.getProfile(userDetails.getUsername());
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/availability/{therapistId}")
    public ResponseEntity<List<Availability>> saveAvailability(@PathVariable Long therapistId, @RequestBody List<AvailabilityRequest> request) {
        List<Availability> slots = therapistService.saveAvailability(therapistId, request);
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/availability/{therapistId}")
    public ResponseEntity<List<Availability>> getAvailability(@PathVariable Long therapistId) {
        List<Availability> slots = therapistService.getAvailability(therapistId);
        return ResponseEntity.ok(slots);
    }
}
