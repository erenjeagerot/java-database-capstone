package com.project.back_end.services;

import com.project.back_end.models.Doctor;
import com.project.back_end.repo.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<String> getAvailableTimesBySpecialtyAndDate(Long doctorId, String specialty, String date) {
        LocalDate requestedDate = LocalDate.parse(date);
        String dayToken = requestedDate.getDayOfWeek().getDisplayName(java.time.format.TextStyle.SHORT, Locale.ENGLISH);

        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (doctor.isEmpty()) {
            return List.of();
        }

        Doctor selectedDoctor = doctor.get();
        if (!selectedDoctor.getSpecialty().equalsIgnoreCase(specialty)) {
            return List.of();
        }

        return selectedDoctor.getAvailableTimes().stream()
                .filter(slot -> slot.toLowerCase(Locale.ENGLISH).contains(dayToken.toLowerCase(Locale.ENGLISH)))
                .collect(Collectors.toList());
    }

    public Map<String, Object> validateDoctorCredentials(String email, String password) {
        return doctorRepository.findByEmailAndPassword(email, password)
                .<Map<String, Object>>map(doctor -> Map.of(
                        "status", "success",
                        "message", "Login successful",
                        "doctorId", doctor.getId(),
                        "doctorName", doctor.getName(),
                        "email", doctor.getEmail()))
                .orElseGet(() -> Map.of(
                        "status", "error",
                        "message", "Invalid email or password"));
    }
}
