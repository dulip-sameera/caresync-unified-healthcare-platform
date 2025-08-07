package dev.dulipsameera.patientservice.service;

import dev.dulipsameera.patientservice.dto.PatientDto;
import dev.dulipsameera.patientservice.entity.PatientEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {
    public List<PatientEntity> getAllPatients();
    public PatientEntity savePatient(PatientDto patientDto);
}
