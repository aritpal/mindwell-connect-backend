package com.aritpal.mindwell_connect.service;

import com.aritpal.mindwell_connect.dto.ParsedAvailabilitySlot;
import com.aritpal.mindwell_connect.dto.TherapistPublicProfileResponse;
import com.aritpal.mindwell_connect.dto.TherapistSummaryDto;
import com.aritpal.mindwell_connect.entity.Availability;
import com.aritpal.mindwell_connect.entity.TherapistProfile;
import com.aritpal.mindwell_connect.repository.AvailabilityRepository;
import com.aritpal.mindwell_connect.repository.TherapistProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class PublicTherapistService {

    @Autowired
    private TherapistProfileRepository profileRepo;

    @Autowired
    private AvailabilityRepository availabilityRepo;

    public List<TherapistSummaryDto> getTherapistList(String specialty) {
        List<TherapistProfile> profiles;
        if (specialty != null && !specialty.isBlank()) {
            profiles = profileRepo.findBySpecialtiesContainingIgnoreCase(specialty);
        } else {
            profiles = profileRepo.findAll();
        }

        return profiles.stream().map(p -> {
            TherapistSummaryDto dto = new TherapistSummaryDto();
            dto.setId(p.getId());
            dto.setFullName(p.getFullName());
            dto.setSpecialties(p.getSpecialties());
            dto.setConsultationFee(p.getConsultationFee());
            return dto;
        }).collect(Collectors.toList());
    }

    public TherapistPublicProfileResponse getTherapistDetails(Long id) {
        TherapistProfile profile = profileRepo.findById(id).orElseThrow(() -> new RuntimeException("Therapist not found"));

        List<Availability> slots = availabilityRepo.findByTherapistProfile_Id(id);

        Map<String, List<ParsedAvailabilitySlot>> parsed = new TreeMap<>();
        for (Availability slot : slots) {
            String day = slot.getDayOfWeek().toString();
            LocalTime start = slot.getStartTime().toLocalTime();
            LocalTime end = slot.getEndTime().toLocalTime();

            parsed.computeIfAbsent(day, k -> new ArrayList<>()).add(new ParsedAvailabilitySlot(start, end));
        }

        TherapistSummaryDto summary = new TherapistSummaryDto();
        summary.setId(profile.getId());
        summary.setFullName(profile.getFullName());
        summary.setSpecialties(profile.getSpecialties());
        summary.setConsultationFee(profile.getConsultationFee());

        TherapistPublicProfileResponse response = new TherapistPublicProfileResponse();
        response.setProfile(summary);
        response.setAvailability(parsed);

        return response;
    }
}
