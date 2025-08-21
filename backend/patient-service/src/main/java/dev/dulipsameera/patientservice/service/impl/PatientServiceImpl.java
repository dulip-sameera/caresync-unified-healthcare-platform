package dev.dulipsameera.patientservice.service.impl;

import dev.dulipsameera.patientservice.dto.PatientDto;
import dev.dulipsameera.patientservice.entity.PatientEntity;
import dev.dulipsameera.patientservice.exception.custom.PatientNotFoundException;
import dev.dulipsameera.patientservice.repository.PatientRepository;
import dev.dulipsameera.patientservice.repository.PatientStatusRepository;
import dev.dulipsameera.patientservice.service.PatientService;
import dev.dulipsameera.patientservice.utils.mapper.PatientMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;
    private PatientStatusRepository patientStatusRepository;
    private PatientMapper patientMapper;

    public PatientServiceImpl(
            PatientRepository patientRepository,
            PatientStatusRepository patientStatusRepository,
            PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientStatusRepository = patientStatusRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public Slice<PatientDto> getAllPatients(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Slice<PatientEntity> patientSlice = patientRepository.findAll(pageable);
        return patientSlice.map((patient) -> patientMapper.toDto(patient));
    }

    @Override
    public PatientDto getPatientById(UUID id) {
        PatientEntity patientEntity = patientRepository
                .findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient with ID " + id + " not found."));
        return patientMapper.toDto(patientEntity);
    }

}
