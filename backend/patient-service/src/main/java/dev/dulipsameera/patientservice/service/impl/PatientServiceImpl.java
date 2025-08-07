package dev.dulipsameera.patientservice.service.impl;

import dev.dulipsameera.patientservice.dto.PatientDto;
import dev.dulipsameera.patientservice.entity.PatientEntity;
import dev.dulipsameera.patientservice.enums.PatientStatusEnum;
import dev.dulipsameera.patientservice.repository.PatientRepository;
import dev.dulipsameera.patientservice.service.PatientService;
import dev.dulipsameera.patientservice.utils.generators.PatientShareIDGenerator;
import dev.dulipsameera.patientservice.utils.mapper.PatientMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientServiceImpl  implements PatientService {

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<PatientDto> getAllPatients() {
        List<PatientEntity> patients = patientRepository.findAll();
        List<PatientDto> patientDtos = new ArrayList<>();
        patients.forEach(entity -> {
            patientDtos.add(PatientMapper.toDto(entity));
        });
        return patientDtos;
    }

    @Override
    public PatientDto savePatient(PatientDto patientDto) {
        PatientEntity patientEntity = PatientMapper.toEntity(patientDto);
        patientEntity.setShareId(generateUniqueShareId());
        patientEntity.setCreatedAt(LocalDateTime.now());
        patientEntity.setStatus(PatientStatusEnum.ACTIVE);
        PatientEntity saved = patientRepository.save(patientEntity);
        return PatientMapper.toDto(saved);
    }

    public String generateUniqueShareId() {
        String shareId;
        do {
            shareId = PatientShareIDGenerator.generateShareId();
        } while (patientRepository.existsByShareId(shareId));
        return shareId;
    }
}
