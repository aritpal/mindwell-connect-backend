package com.aritpal.mindwell_connect.repository;

import com.aritpal.mindwell_connect.entity.Appointment;
import com.aritpal.mindwell_connect.entity.AppointmentStatus;
import com.aritpal.mindwell_connect.entity.PatientProfile;
import com.aritpal.mindwell_connect.entity.TherapistProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatient(PatientProfile patient);

    List<Appointment> findByTherapist(TherapistProfile therapist);

    boolean existsByTherapistAndStartTimeAndEndTimeAndStatusNot(TherapistProfile therapist, LocalDateTime startTime, LocalDateTime endTime, AppointmentStatus status);

}
