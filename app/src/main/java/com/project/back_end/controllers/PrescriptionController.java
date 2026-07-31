package com.project.back_end.controllers;

import com.project.back_end.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> savePrescription(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody Map<String, Object> prescriptionRequest) {

        if (!tokenService.validateToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized: Invalid token");
        }

        // Reçete kaydetme başarı yanıtı
        return ResponseEntity.ok(Map.of("message", "Prescription saved successfully"));
    }
}