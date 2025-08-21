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

    private final PatientRepository patientRepository;
    private final PatientStatusRepository patientStatusRepository;
    private final PatientMapper patientMapper;

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
        return patientSlice.map(patientMapper::toDto);
    }

    @Override
    public PatientDto getPatientById(UUID id) {
        PatientEntity patientEntity = patientRepository
                .findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient with ID " + id + " not found."));
        return patientMapper.toDto(patientEntity);
    }

    @Override
    public PatientDto getPatientByShareId(String shareId) {
        PatientEntity patientEntity = patientRepository
                .findByShareId(shareId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with shareId " + shareId + " not found."));
        return patientMapper.toDto(patientEntity);
    }

    @Override
    public PatientDto getPatientByContact(String contact) {
        PatientEntity patientEntity = patientRepository
                .findByContactNo(contact)
                .orElseThrow(() -> new PatientNotFoundException("Patient with contact " + contact + " not found."));
        return patientMapper.toDto(patientEntity);
    }

}
