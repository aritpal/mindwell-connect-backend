package com.aritpal.mindwell_connect.service;

import com.aritpal.mindwell_connect.dto.AppointmentRequest;
import com.aritpal.mindwell_connect.dto.AppointmentResponse;
import com.aritpal.mindwell_connect.entity.*;
import com.aritpal.mindwell_connect.repository.AppointmentRepository;
import com.aritpal.mindwell_connect.repository.PatientProfileRepository;
import com.aritpal.mindwell_connect.repository.TherapistProfileRepository;
import com.aritpal.mindwell_connect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PatientProfileRepository patientProfileRepo;

    @Autowired
    private TherapistProfileRepository therapistProfileRepo;

    public AppointmentResponse book(String patientEmail, AppointmentRequest request) {
        User user = userRepo.findByEmail(patientEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        PatientProfile patient = patientProfileRepo.findByUser(user).orElseThrow(() -> new RuntimeException("Patient profile not found"));

        TherapistProfile therapist = therapistProfileRepo.findById(request.getTherapistId()).orElseThrow(() -> new RuntimeException("Therapist profile not found"));

        boolean isConflict = appointmentRepo.existsByTherapistAndStartTimeAndEndTimeAndStatusNot(therapist, request.getStartTime(), request.getEndTime(), AppointmentStatus.CANCELLED);

        if (isConflict) throw new RuntimeException("Therapist already has an active appointment during that time.");

        Appointment appointment = Appointment.builder().patient(patient).therapist(therapist).startTime(request.getStartTime()).endTime(request.getEndTime()).status(AppointmentStatus.BOOKED).build();

        return mapToDto(appointmentRepo.save(appointment));
    }

    public List<AppointmentResponse> getPatientAppointments(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        PatientProfile patient = patientProfileRepo.findByUser(user).orElseThrow(() -> new RuntimeException("Patient profile not found"));

        return appointmentRepo.findByPatient(patient).stream().map(this::mapToDto).toList();
    }

    public List<AppointmentResponse> getTherapistAppointments(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        TherapistProfile therapist = therapistProfileRepo.findByUser(user).orElseThrow(() -> new RuntimeException("Therapist profile not found"));

        return appointmentRepo.findByTherapist(therapist).stream().map(this::mapToDto).toList();
    }

    public AppointmentResponse updateStatus(Long id, String email, AppointmentStatus newStatus) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));

        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean isPatient = appointment.getPatient().getUser().getId().equals(user.getId());
        boolean isTherapist = appointment.getTherapist().getUser().getId().equals(user.getId());

        switch (newStatus) {
            case CANCELLED:
                if (!isPatient || appointment.getStatus() != AppointmentStatus.BOOKED) {
                    throw new RuntimeException("Only patients can cancel a BOOKED appointment.");
                }
                break;
            case CONFIRMED:
            case COMPLETED:
                if (!isTherapist) throw new RuntimeException("Only therapists can update to this status.");
                break;
            default:
                throw new RuntimeException("Invalid status.");
        }

        appointment.setStatus(newStatus);
        return mapToDto(appointmentRepo.save(appointment));
    }

    private AppointmentResponse mapToDto(Appointment appointment) {
        AppointmentResponse dto = new AppointmentResponse();
        dto.setId(appointment.getId());
        dto.setStartTime(appointment.getStartTime());
        dto.setEndTime(appointment.getEndTime());
        dto.setStatus(appointment.getStatus());
        dto.setCreatedAt(appointment.getCreatedAt());
        dto.setPatientName(appointment.getPatient().getFullName());
        dto.setTherapistName(appointment.getTherapist().getFullName());
        return dto;
    }
}
