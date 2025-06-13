package com.aritpal.mindwell_connect.service;

import com.aritpal.mindwell_connect.dto.AppointmentRequest;
import com.aritpal.mindwell_connect.dto.AppointmentResponse;
import com.aritpal.mindwell_connect.entity.Appointment;
import com.aritpal.mindwell_connect.entity.AppointmentStatus;
import com.aritpal.mindwell_connect.entity.User;
import com.aritpal.mindwell_connect.repository.AppointmentRepository;
import com.aritpal.mindwell_connect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private UserRepository userRepo;

    public AppointmentResponse bookAppointment(String patientEmail, AppointmentRequest request) {
        User patient = userRepo.findByEmail(patientEmail).orElseThrow(() -> new UsernameNotFoundException("Patient not found"));

        User therapist = userRepo.findById(request.getTherapistId()).orElseThrow(() -> new RuntimeException("Therapist not found"));

        boolean exists = appointmentRepo.existsByTherapistAndStartTimeAndEndTime(therapist, request.getStartTime(), request.getEndTime());

        if (exists) {
            throw new RuntimeException("This time slot is already booked.");
        }

        Appointment appointment = Appointment.builder().patient(patient).therapist(therapist).startTime(request.getStartTime()).endTime(request.getEndTime()).status(AppointmentStatus.BOOKED).build();

        Appointment saved = appointmentRepo.save(appointment);
        return mapToDto(saved);
    }

    public List<AppointmentResponse> getPatientAppointments(String email) {
        User patient = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Patient not found"));

        return appointmentRepo.findByPatient(patient).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<AppointmentResponse> getTherapistAppointments(String email) {
        User therapist = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Therapist not found"));

        return appointmentRepo.findByTherapist(therapist).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public AppointmentResponse updateStatus(Long appointmentId, String email, AppointmentStatus newStatus) {
        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(() -> new RuntimeException("Appointment not found"));

        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean isPatient = appointment.getPatient().getId().equals(user.getId());
        boolean isTherapist = appointment.getTherapist().getId().equals(user.getId());

        switch (newStatus) {
            case CANCELLED:
                if (!isPatient) throw new RuntimeException("Only patients can cancel appointments");
                if (appointment.getStatus() != AppointmentStatus.BOOKED) {
                    throw new RuntimeException("Only BOOKED appointments can be cancelled");
                }
                break;

            case CONFIRMED:
            case COMPLETED:
                if (!isTherapist) throw new RuntimeException("Only therapists can change to " + newStatus);
                break;

            default:
                throw new RuntimeException("Invalid status change");
        }

        appointment.setStatus(newStatus);
        return mapToDto(appointmentRepo.save(appointment));
    }


    private AppointmentResponse mapToDto(Appointment a) {
        AppointmentResponse dto = new AppointmentResponse();
        dto.setId(a.getId());
        dto.setTherapistName(a.getTherapist().getEmail()); // or full name if available
        dto.setPatientEmail(a.getPatient().getEmail());
        dto.setStartTime(a.getStartTime());
        dto.setEndTime(a.getEndTime());
        dto.setStatus(a.getStatus());
        return dto;
    }
}
