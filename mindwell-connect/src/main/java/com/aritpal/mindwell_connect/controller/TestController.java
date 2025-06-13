package com.aritpal.mindwell_connect.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/patient")
    @PreAuthorize("hasRole('PATIENT')")
    public String patientAccess() {
        return "Hello Patient! You have access to patient endpoint.";
    }

    @GetMapping("/therapist")
    @PreAuthorize("hasRole('THERAPIST')")
    public String therapistAccess() {
        return "Hello Therapist! You have access to therapist endpoint.";
    }
}
