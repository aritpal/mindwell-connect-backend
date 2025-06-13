package com.aritpal.mindwell_connect.repository;

import com.aritpal.mindwell_connect.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    List<Availability> findByTherapistProfile_Id(Long therapistId);

}
