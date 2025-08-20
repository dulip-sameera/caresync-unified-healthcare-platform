package dev.dulipsameera.patientservice.service;

import dev.dulipsameera.patientservice.dto.PatientDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {
    List<PatientDto> getAllPatients();
}
