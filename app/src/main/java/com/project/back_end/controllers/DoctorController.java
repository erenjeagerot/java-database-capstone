package com.project.back_end.controllers;

import com.project.back_end.services.DoctorService;
import com.project.back_end.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/{user}/{doctorId}/availability")
    public ResponseEntity<?> getDoctorAvailability(
            @RequestHeader("Authorization") String token,
            @PathVariable String user,
            @PathVariable Long doctorId,
            @RequestParam String specialty,
            @RequestParam String date) {

        if (!tokenService.validateToken(token)) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid or missing token"));
        }

        List<String> availableSlots = doctorService.getAvailableTimesBySpecialtyAndDate(doctorId, specialty, date);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("user", user);
        response.put("doctorId", doctorId);
        response.put("specialty", specialty);
        response.put("date", date);
        response.put("availableSlots", availableSlots);
        response.put("message", "Doctor availability retrieved successfully");

        return ResponseEntity.ok(response);
    }
}
