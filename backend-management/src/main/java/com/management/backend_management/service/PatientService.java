package com.management.backend_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.management.backend_management.entity.Patient;
import com.management.backend_management.repository.PatientRepository;

@Service
public class PatientService {
    
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient removePatient(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            patientRepository.delete(patient.get());
            return patient.get();
        }
        return null;
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }
    
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient updatePatient(Long id, Patient patient) {
        Optional<Patient> exisiting = patientRepository.findById(id);
        if (exisiting.isEmpty()) {
            return null;
        }
        Patient updated = exisiting.get();
        updated.setFirstName(patient.getFirstName());
        updated.setLastName(patient.getLastName());
        updated.setEmail(patient.getEmail());
        
        return patientRepository.save(updated);
    }
}
