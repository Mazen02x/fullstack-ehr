package com.management.backend_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.backend_management.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    
}
