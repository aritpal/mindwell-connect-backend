package com.aritpal.mindwell_connect.service;

import com.aritpal.mindwell_connect.dto.PatientProfileRequest;
import com.aritpal.mindwell_connect.dto.PatientProfileResponse;
import com.aritpal.mindwell_connect.entity.PatientProfile;
import com.aritpal.mindwell_connect.entity.User;
import com.aritpal.mindwell_connect.repository.PatientProfileRepository;
import com.aritpal.mindwell_connect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PatientProfileService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PatientProfileRepository profileRepo;

    public PatientProfileResponse createOrUpdate(String email, PatientProfileRequest request) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        PatientProfile profile = profileRepo.findByUser(user).orElse(new PatientProfile());
        profile.setUser(user);
        profile.setFullName(request.getFullName());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setGender(request.getGender());
        profile.setPhone(request.getPhone());
        profile.setConcerns(request.getConcerns());

        PatientProfile saved = profileRepo.save(profile);
        return mapToResponse(saved);
    }

    public PatientProfileResponse getOwnProfile(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        PatientProfile profile = profileRepo.findByUser(user).orElseThrow(() -> new RuntimeException("Profile not found"));

        return mapToResponse(profile);
    }

    private PatientProfileResponse mapToResponse(PatientProfile profile) {
        PatientProfileResponse dto = new PatientProfileResponse();
        dto.setId(profile.getId());
        dto.setFullName(profile.getFullName());
        dto.setDateOfBirth(profile.getDateOfBirth());
        dto.setGender(profile.getGender());
        dto.setPhone(profile.getPhone());
        dto.setConcerns(profile.getConcerns());
        dto.setCreatedAt(profile.getCreatedAt());
        dto.setUpdatedAt(profile.getUpdatedAt());
        return dto;
    }
}
