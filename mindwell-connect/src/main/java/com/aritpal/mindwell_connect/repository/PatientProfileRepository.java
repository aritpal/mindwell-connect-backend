package com.aritpal.mindwell_connect.repository;

import com.aritpal.mindwell_connect.entity.PatientProfile;
import com.aritpal.mindwell_connect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {
    Optional<PatientProfile> findByUser(User user);
}
