package dev.dulipsameera.patientservice.service.impl;

import dev.dulipsameera.patientservice.dto.PatientDto;
import dev.dulipsameera.patientservice.entity.PatientEntity;
import dev.dulipsameera.patientservice.entity.PatientStatusEntity;
import dev.dulipsameera.patientservice.entity.embedded.PatientAddress;
import dev.dulipsameera.patientservice.enums.PatientStatusEnum;
import dev.dulipsameera.patientservice.exception.custom.PatientCreationException;
import dev.dulipsameera.patientservice.exception.custom.PatientDtoValidationException;
import dev.dulipsameera.patientservice.exception.custom.PatientNotFoundException;
import dev.dulipsameera.patientservice.exception.custom.PatientStatusNotFoundException;
import dev.dulipsameera.patientservice.repository.PatientRepository;
import dev.dulipsameera.patientservice.repository.PatientStatusRepository;
import dev.dulipsameera.patientservice.service.PatientService;
import dev.dulipsameera.patientservice.utils.generator.PatientShareIdGenerator;
import dev.dulipsameera.patientservice.utils.mapper.PatientMapper;
import dev.dulipsameera.patientservice.utils.validator.PatientDtoValidator;
import org.springframework.dao.DataIntegrityViolationException;
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
    public PatientDto getPatientByUserId(UUID userId) {
        PatientEntity patientEntity = patientRepository
                .findByUserId(userId)
                .orElseThrow(() -> new PatientNotFoundException("Patient with userId " + userId + " not found."));
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
            throw new PatientStatusNotFoundException("Patient status with ID " + PatientStatusEnum.DELETED.getId() + " not found.");
        }
        patientEntity.setStatusId(patientStatusEntity.get());
        patientRepository.save(patientEntity);
    }

    @Override
    @Transactional
    public PatientDto createPatient(PatientDto patientDto) {
        try {
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
                throw new PatientStatusNotFoundException("Patient status with ID " + PatientStatusEnum.ACTIVE.getId() + " not found.");
            }
            patientEntity.setStatusId(activeStatus.get());
            // save the patient entity
            // map the patient entity to the patient DTO
            // return the patient DTO
            return patientMapper.toDto(patientRepository.save(patientEntity));
        } catch (PatientDtoValidationException | PatientStatusNotFoundException ex) {
            throw ex;
        } catch (DataIntegrityViolationException ex) {
            throw new PatientCreationException("Duplicate or invalid patient data: " + ex.getMostSpecificCause().getMessage(), ex);
        } catch (Exception ex) {
            throw new PatientCreationException("Unexpected error occurred while creating patient.", ex);
        }
    }

    @Override
    @Transactional
    public PatientDto updatePatient(UUID id, PatientDto patientDto) {
        try {
            // check if the id is null
            if (id == null) {
                throw new IllegalArgumentException("Patient ID cannot be null");
            }
            // check if the patient DTO is null
            if (patientDto == null) {
                throw new IllegalArgumentException("PatientDto cannot be null");
            }
            // get the patient entity by id and throw exception if not found
            PatientEntity patientEntity = patientRepository
                    .findById(id)
                    .orElseThrow(() -> new PatientNotFoundException("Patient with ID " + id + " not found."));

            // validate the values of the patient DTO
            patientDtoValidator.validate(patientDto);
            // update the patient entity with the new values
            // save the patient entity
            PatientEntity updatedPatient = patientRepository.save(
                    updatePatientEntity(patientEntity, patientDto, patientStatusRepository));
            // map the patient entity to the patient DTO
            // return the patient DTO
            return patientMapper.toDto(updatedPatient);
        } catch (PatientDtoValidationException | IllegalArgumentException | PatientNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PatientCreationException("Unexpected error occurred while updating patient.", ex);
        }
    }

    private PatientEntity updatePatientEntity(
            PatientEntity patientEntity,
            PatientDto patientDto,
            PatientStatusRepository patientStatusRepository) {
        if (patientDto == null) {
            throw new IllegalArgumentException("PatientDto cannot be null");
        }

        if (patientDto.getFirstName() != null) {
            patientEntity.setFirstName(patientDto.getFirstName());
        }
        if (patientDto.getLastName() != null) {
            patientEntity.setLastName(patientDto.getLastName());
        }
        if (patientDto.getEmail() != null) {
            patientEntity.setEmail(patientDto.getEmail());
        }
        if (patientDto.getNic() != null) {
            patientEntity.setNic(patientDto.getNic());
        }
        if (patientDto.getContactNo() != null) {
            patientEntity.setContactNo(patientDto.getContactNo());
        }
        if (patientDto.getDateOfBirth() != null) {
            patientEntity.setDateOfBirth(patientDto.getDateOfBirth());
        }
        // address (embedded object)
        if (patientDto.getAddress() != null) {
            if (patientEntity.getAddress() == null) {
                patientEntity.setAddress(new PatientAddress());
            }
            if (patientDto.getAddress().getStreet1() != null) {
                patientEntity.getAddress().setStreet1(patientDto.getAddress().getStreet1());
            }

            // street2 is optional, so no need to check if it's null or not'
            patientEntity.getAddress().setStreet2(patientDto.getAddress().getStreet2());

            if (patientDto.getAddress().getCity() != null) {
                patientEntity.getAddress().setCity(patientDto.getAddress().getCity());
            }
            if (patientDto.getAddress().getDistrict() != null) {
                patientEntity.getAddress().setDistrict(patientDto.getAddress().getDistrict());
            }
            if (patientDto.getAddress().getProvince() != null) {
                patientEntity.getAddress().setProvince(patientDto.getAddress().getProvince());
            }
            if (patientDto.getAddress().getZipCode() != null) {
                patientEntity.getAddress().setZipCode(patientDto.getAddress().getZipCode());
            }
        }

        // update status
        if (patientDto.getStatus() != null) {
            PatientStatusEnum status = PatientStatusEnum.valueOf(patientDto.getStatus());
            Optional<PatientStatusEntity> statusEntity = patientStatusRepository.findById(status.getId());
            if (statusEntity.isEmpty()) {
                throw new PatientStatusNotFoundException("Patient status with ID " + status.getId() + " not found.");
            }
            patientEntity.setStatusId(statusEntity.get());
        }

        patientEntity.setUpdatedAt(LocalDateTime.now());

        return patientEntity;
    }

}
