package com.project.back_end.controllers;

import com.project.back_end.models.Doctor;
import com.project.back_end.services.DoctorService;
import com.project.back_end.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/availability")
    public ResponseEntity<?> getDoctorAvailability(
            @RequestHeader("Authorization") String token,
            @RequestParam String specialty,
            @RequestParam String date) {
        
        if (!tokenService.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid or missing token");
        }

        List<String> availableSlots = doctorService.getAvailableTimesBySpecialtyAndDate(specialty, date);
        return ResponseEntity.ok(availableSlots);
    }
}