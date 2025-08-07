package dev.dulipsameera.patientservice.service.impl;

import dev.dulipsameera.patientservice.dto.PatientDto;
import dev.dulipsameera.patientservice.entity.PatientEntity;
import dev.dulipsameera.patientservice.repository.PatientRepository;
import dev.dulipsameera.patientservice.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl  implements PatientService {

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<PatientEntity> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public PatientEntity savePatient(PatientDto patientDto) {
        return null;
    }
}
