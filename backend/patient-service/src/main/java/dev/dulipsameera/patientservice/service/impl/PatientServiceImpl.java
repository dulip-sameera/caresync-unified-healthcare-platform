package dev.dulipsameera.patientservice.service.impl;

import dev.dulipsameera.patientservice.dto.PatientDto;
import dev.dulipsameera.patientservice.entity.PatientEntity;
import dev.dulipsameera.patientservice.entity.PatientStatusEntity;
import dev.dulipsameera.patientservice.enums.PatientStatusEnum;
import dev.dulipsameera.patientservice.exception.custom.PatientNotFoundException;
import dev.dulipsameera.patientservice.exception.custom.PatientStatusNotFound;
import dev.dulipsameera.patientservice.repository.PatientRepository;
import dev.dulipsameera.patientservice.repository.PatientStatusRepository;
import dev.dulipsameera.patientservice.service.PatientService;
import dev.dulipsameera.patientservice.utils.generator.PatientShareIdGenerator;
import dev.dulipsameera.patientservice.utils.mapper.PatientMapper;
import dev.dulipsameera.patientservice.utils.validator.PatientDtoValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientStatusRepository patientStatusRepository;
    private final PatientMapper patientMapper;
    private final PatientDtoValidator patientDtoValidator;
    private final PatientShareIdGenerator patientShareIdGenerator;

    public PatientServiceImpl(
            PatientRepository patientRepository,
            PatientStatusRepository patientStatusRepository,
            PatientMapper patientMapper,
            PatientDtoValidator patientDtoValidator,
            PatientShareIdGenerator patientShareIdGenerator) {
        this.patientRepository = patientRepository;
        this.patientStatusRepository = patientStatusRepository;
        this.patientMapper = patientMapper;
        this.patientDtoValidator = patientDtoValidator;
        this.patientShareIdGenerator = patientShareIdGenerator;
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

    @Override
    public PatientDto getPatientByNic(String nic) {
        PatientEntity patientEntity = patientRepository
                .findByNic(nic)
                .orElseThrow(() -> new PatientNotFoundException("Patient with nic " + nic + " not found."));
        return patientMapper.toDto(patientEntity);
    }

    @Override
    public PatientDto getPatientByEmail(String email) {
        PatientEntity patientEntity = patientRepository
                .findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient with email " + email + " not found."));
        return patientMapper.toDto(patientEntity);
    }

    @Override
    @Transactional
    public void deletePatientById(UUID id) {
        // check if the id is null
        if (id == null) {
            throw new IllegalArgumentException("Patient ID cannot be null");
        }

        // get the patient entity by id and throw exception if not found
        PatientEntity patientEntity = patientRepository
                .findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient with ID " + id + " not found."));
        // check the current status of the patient
        if (PatientStatusEnum.DELETED.getId().equals(patientEntity.getStatusId().getId())) {
            throw new PatientNotFoundException("Patient with ID " + id + " is already deleted.");
        }
        // mark the patient as deleted
        Optional<PatientStatusEntity> patientStatusEntity = patientStatusRepository
                .findById(PatientStatusEnum.DELETED.getId());
        if (patientStatusEntity.isEmpty()) {
            throw new PatientStatusNotFound("Patient status with ID " + PatientStatusEnum.DELETED.getId() + " not found.");
        }
        patientEntity.setStatusId(patientStatusEntity.get());
        patientRepository.save(patientEntity);
    }

    @Override
    @Transactional
    public PatientDto createPatient(PatientDto patientDto) {
        // validate the values of the patient DTO
        patientDtoValidator.validate(patientDto);
        // generate the shareid
        String shareId = patientShareIdGenerator.generateShareId();
        // create the patient entity
        PatientEntity patientEntity = patientMapper.toEntity(patientDto);
        patientEntity.setShareId(shareId);
        patientEntity.setCreatedAt(LocalDateTime.now());
        Optional<PatientStatusEntity> activeStatus = patientStatusRepository.findById(PatientStatusEnum.ACTIVE.getId());
        if (activeStatus.isEmpty()) {
            throw new PatientStatusNotFound("Patient status with ID " + PatientStatusEnum.ACTIVE.getId() + " not found.");
        }
        patientEntity.setStatusId(activeStatus.get());
        // save the patient entity
        // map the patient entity to the patient DTO
        // return the patient DTO
        return patientMapper.toDto(patientRepository.save(patientEntity));
    }

}
