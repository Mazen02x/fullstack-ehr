package com.management.backend_management.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.backend_management.dto.PatientRequest;
import com.management.backend_management.entity.Patient;
import com.management.backend_management.service.PatientService;

import jakarta.validation.Valid;

import com.management.backend_management.dto.PatientResponse;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    
    private final PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientResponse> getAll() {
        List<Patient> patients = patientService.findAll();
        List<PatientResponse> response = new ArrayList<>();
        for (Patient patient : patients) {
            response.add(toResponse(patient));
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getById(@PathVariable Long id) {
        return patientService.findById(id)
            .map(patient -> ResponseEntity.ok(toResponse(patient)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PatientResponse> create(@Valid @RequestBody PatientRequest request) {
        Patient created = patientService.createPatient(toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Patient removed = patientService.removePatient(id);
        if (removed != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> update(@PathVariable Long id, @Valid @RequestBody PatientRequest request) {
        Patient updated = patientService.updatePatient(id, toEntity(request));
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponse(updated));
    }

    private Patient toEntity(PatientRequest request) {
        Patient patient = new Patient();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        return patient;
    }

    private PatientResponse toResponse(Patient patient) {
        PatientResponse response = new PatientResponse();
        response.setId(patient.getId());
        response.setFirstName(patient.getFirstName());
        response.setLastName(patient.getLastName());
        response.setEmail(patient.getEmail());
        return response;
    }
}
