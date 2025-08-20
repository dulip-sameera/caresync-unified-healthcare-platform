package dev.dulipsameera.patientservice.service.impl;

import dev.dulipsameera.patientservice.dto.PatientDto;
import dev.dulipsameera.patientservice.repository.PatientRepository;
import dev.dulipsameera.patientservice.repository.PatientStatusRepository;
import dev.dulipsameera.patientservice.service.PatientService;
import dev.dulipsameera.patientservice.utils.mapper.PatientMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;
    private PatientStatusRepository patientStatusRepository;

    public PatientServiceImpl(PatientRepository patientRepository, PatientStatusRepository patientStatusRepository) {
        this.patientRepository = patientRepository;
        this.patientStatusRepository = patientStatusRepository;
    }

    @Override
    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll().stream().map(PatientMapper::toDto).toList();
    }
}
