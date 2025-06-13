package com.aritpal.mindwell_connect.service;

import com.aritpal.mindwell_connect.dto.AvailabilityRequest;
import com.aritpal.mindwell_connect.dto.TherapistProfileRequest;
import com.aritpal.mindwell_connect.dto.TherapistProfileResponse;
import com.aritpal.mindwell_connect.entity.Availability;
import com.aritpal.mindwell_connect.entity.TherapistProfile;
import com.aritpal.mindwell_connect.entity.User;
import com.aritpal.mindwell_connect.repository.AvailabilityRepository;
import com.aritpal.mindwell_connect.repository.TherapistProfileRepository;
import com.aritpal.mindwell_connect.repository.UserRepository;
import com.aritpal.mindwell_connect.util.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TherapistService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TherapistProfileRepository profileRepo;

    @Autowired
    private AvailabilityRepository availabilityRepo;

    public TherapistProfileResponse createOrUpdateProfile(String email, TherapistProfileRequest req) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        TherapistProfile profile = profileRepo.findByUser(user).orElse(new TherapistProfile());

        profile.setUser(user);
        profile.setFullName(req.getFullName());
        profile.setSpecialties(req.getSpecialties());
        profile.setQualifications(req.getQualifications());
        profile.setBio(req.getBio());
        profile.setConsultationFee(req.getConsultationFee());

        TherapistProfile savedProfile = profileRepo.save(profile);

        TherapistProfileResponse response = new TherapistProfileResponse();
        response.setId(savedProfile.getId());
        response.setFullName(savedProfile.getFullName());
        response.setSpecialties(savedProfile.getSpecialties());
        response.setQualifications(savedProfile.getQualifications());
        response.setBio(savedProfile.getBio());
        response.setConsultationFee(savedProfile.getConsultationFee());
        response.setUser(UserMapper.toDto(savedProfile.getUser()));

        return response;
    }

    public TherapistProfileResponse getProfile(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        TherapistProfile profile = profileRepo.findByUser(user).orElseThrow(() -> new RuntimeException("Therapist profile not found"));

        TherapistProfileResponse response = new TherapistProfileResponse();
        response.setId(profile.getId());
        response.setFullName(profile.getFullName());
        response.setSpecialties(profile.getSpecialties());
        response.setQualifications(profile.getQualifications());
        response.setBio(profile.getBio());
        response.setConsultationFee(profile.getConsultationFee());
        response.setUser(UserMapper.toDto(profile.getUser()));
        response.setCreatedAt(profile.getCreatedAt());
        response.setUpdatedAt(profile.getUpdatedAt());

        return response;
    }

    @Transactional
    public List<Availability> saveAvailability(Long therapistId, List<AvailabilityRequest> requests) {
        TherapistProfile therapist = profileRepo.findById(therapistId).orElseThrow(() -> new RuntimeException("Therapist not found"));

        List<Availability> saved = new ArrayList<>();
        for (AvailabilityRequest req : requests) {
            Availability availability = Availability.builder().dayOfWeek(req.getDayOfWeek()).startTime(req.getStartTime()).endTime(req.getEndTime()).therapistProfile(therapist).build();
            saved.add(availability);
        }

        return availabilityRepo.saveAll(saved);
    }

    public List<Availability> getAvailability(Long therapistId) {
        return availabilityRepo.findByTherapistProfile_Id(therapistId);
    }
}
