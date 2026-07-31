package com.project.back_end.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DoctorService {

    public List<String> getAvailableTimesBySpecialtyAndDate(String specialty, String date) {
        // Belirli tarih ve uzmanlığa göre boş saatleri döndürür
        return List.of("09:00 - 10:00", "11:00 - 12:00", "14:00 - 15:00");
    }

    public Map<String, Object> validateDoctorCredentials(String email, String password) {
        // Doktor kimlik bilgilerini doğrulama mantığı
        return Map.of("status", "success", "message", "Login successful");
    }
}