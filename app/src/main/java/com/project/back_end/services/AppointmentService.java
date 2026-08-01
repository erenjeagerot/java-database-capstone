package com.project.back_end.services;

import com.project.back_end.models.Appointment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    public Appointment bookAppointment(Appointment appointment) {
        // Randevu kaydetme mantığı
        return appointment;
    }

    public List<Appointment> getAppointmentsByDoctorAndDate(Long doctorId, LocalDateTime date) {
        // Belirli bir tarihteki doktor randevularını getirme
        return new ArrayList<>();
    }
}