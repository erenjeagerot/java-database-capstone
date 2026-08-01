package com.project.back_end.repo;

import com.project.back_end.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Finds a patient by email address.
     * Used for patient login and account lookup flows.
     */
    Optional<Patient> findByEmail(String email);

    /**
     * Finds a patient by either email or phone number.
     * Useful when the application accepts multiple identifiers for lookup.
     */
    Optional<Patient> findByEmailOrPhone(String email, String phone);
}
