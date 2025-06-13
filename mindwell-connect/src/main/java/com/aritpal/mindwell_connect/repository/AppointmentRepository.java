package com.aritpal.mindwell_connect.repository;

import com.aritpal.mindwell_connect.entity.Appointment;
import com.aritpal.mindwell_connect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatient(User patient);

    List<Appointment> findByTherapist(User therapist);

    boolean existsByTherapistAndStartTimeAndEndTime(User therapist, LocalDateTime startTime, LocalDateTime endTime);
}
