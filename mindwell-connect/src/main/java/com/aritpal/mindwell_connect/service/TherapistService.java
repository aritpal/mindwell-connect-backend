package com.aritpal.mindwell_connect.service;

import com.aritpal.mindwell_connect.dto.AvailabilityRequest;
import com.aritpal.mindwell_connect.dto.TherapistProfileRequest;
import com.aritpal.mindwell_connect.entity.Availability;
import com.aritpal.mindwell_connect.entity.TherapistProfile;
import com.aritpal.mindwell_connect.entity.User;
import com.aritpal.mindwell_connect.repository.AvailabilityRepository;
import com.aritpal.mindwell_connect.repository.TherapistProfileRepository;
import com.aritpal.mindwell_connect.repository.UserRepository;
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

    public TherapistProfile createOrUpdateProfile(String email, TherapistProfileRequest req) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        TherapistProfile profile = profileRepo.findByUser(user).orElse(new TherapistProfile());
        profile.setUser(user);
        profile.setFullName(req.getFullName());
        profile.setSpecialties(req.getSpecialties());
        profile.setQualifications(req.getQualifications());
        profile.setBio(req.getBio());
        profile.setConsultationFee(req.getConsultationFee());

        return profileRepo.save(profile);
    }

    public TherapistProfile getProfile(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return profileRepo.findByUser(user).orElseThrow(() -> new RuntimeException("Therapist profile not found"));
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
