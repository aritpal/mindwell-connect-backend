package com.aritpal.mindwell_connect.controller;

import com.aritpal.mindwell_connect.dto.TherapistPublicProfileResponse;
import com.aritpal.mindwell_connect.dto.TherapistSummaryDto;
import com.aritpal.mindwell_connect.service.PublicTherapistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/therapists")
public class PublicTherapistController {

    @Autowired
    private PublicTherapistService publicTherapistService;

    @GetMapping
    public List<TherapistSummaryDto> getAllTherapists(@RequestParam(required = false) String specialty) {
        return publicTherapistService.getTherapistList(specialty);
    }

    @GetMapping("/{id}")
    public TherapistPublicProfileResponse getTherapistDetails(@PathVariable Long id) {
        return publicTherapistService.getTherapistDetails(id);
    }
}
