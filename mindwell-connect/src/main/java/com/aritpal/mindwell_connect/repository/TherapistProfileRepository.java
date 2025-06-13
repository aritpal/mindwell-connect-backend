package com.aritpal.mindwell_connect.repository;

import com.aritpal.mindwell_connect.entity.TherapistProfile;
import com.aritpal.mindwell_connect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TherapistProfileRepository extends JpaRepository<TherapistProfile, Long> {

    Optional<TherapistProfile> findByUser(User user);

    List<TherapistProfile> findBySpecialtiesContainingIgnoreCase(String specialty);

}
