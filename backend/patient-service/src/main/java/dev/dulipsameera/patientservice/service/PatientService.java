package dev.dulipsameera.patientservice.service;

import dev.dulipsameera.patientservice.dto.PatientDto;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public interface PatientService {
    Slice<PatientDto> getAllPatients(int page, int pageSize);
    PatientDto getPatientById(UUID id);
    PatientDto getPatientByShareId(String shareId);
    PatientDto getPatientByContact(String contact);
}
