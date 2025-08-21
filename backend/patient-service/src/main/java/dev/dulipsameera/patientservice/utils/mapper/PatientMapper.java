package dev.dulipsameera.patientservice.utils.mapper;

import dev.dulipsameera.patientservice.dto.PatientDto;
import dev.dulipsameera.patientservice.entity.PatientEntity;
import dev.dulipsameera.patientservice.entity.PatientStatusEntity;
import dev.dulipsameera.patientservice.enums.PatientStatusEnum;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    private final PatientAddressMapper patientAddressMapper;

    public PatientMapper(PatientAddressMapper patientAddressMapper) {
        this.patientAddressMapper = patientAddressMapper;
    }

    public PatientDto toDto(PatientEntity patientEntity) {
        PatientDto patientDto = new PatientDto();

        patientDto.setId(patientEntity.getId());
        patientDto.setShareId(patientEntity.getShareId());
        patientDto.setFirstName(patientEntity.getFirstName());
        patientDto.setLastName(patientEntity.getLastName());
        patientDto.setDateOfBirth(patientEntity.getDateOfBirth());
        patientDto.setEmail(patientEntity.getEmail());
        patientDto.setNic(patientEntity.getNic());
        patientDto.setContactNo(patientEntity.getContactNo());
        patientDto.setAddress(patientAddressMapper.toDto(patientEntity.getAddress()));
        patientDto.setUserId(patientEntity.getUserId());
        patientDto.setCreatedAt(patientEntity.getCreatedAt());
        patientDto.setUpdatedAt(patientEntity.getUpdatedAt());

        String status = patientEntity.getStatusId().getId().equals(PatientStatusEnum.ACTIVE.getId()) ?
                PatientStatusEnum.ACTIVE.getName() : PatientStatusEnum.DELETED.getName();
        patientDto.setStatus(status);
        return patientDto;
    }


    public PatientEntity toEntity(PatientDto patientDto) {
        PatientEntity patientEntity = new PatientEntity();

        patientEntity.setId(patientDto.getId());
        patientEntity.setShareId(patientDto.getShareId());
        patientEntity.setFirstName(patientDto.getFirstName());
        patientEntity.setLastName(patientDto.getLastName());
        patientEntity.setDateOfBirth(patientDto.getDateOfBirth());
        patientEntity.setEmail(patientDto.getEmail());
        patientEntity.setNic(patientDto.getNic());
        patientEntity.setContactNo(patientDto.getContactNo());
        patientEntity.setAddress(patientAddressMapper.toEntity(patientDto.getAddress()));
        patientEntity.setUserId(patientDto.getUserId());
        patientEntity.setCreatedAt(patientDto.getCreatedAt());
        patientEntity.setUpdatedAt(patientDto.getUpdatedAt());

        if (patientDto.getStatus() == null) {
            patientEntity.setStatusId(null);
        } else {
            PatientStatusEntity status = patientDto.getStatus().equals(PatientStatusEnum.ACTIVE.getName())
                    ? new PatientStatusEntity(PatientStatusEnum.ACTIVE.getId())
                    : new PatientStatusEntity(PatientStatusEnum.DELETED.getId());

            patientEntity.setStatusId(status);
        }
        return patientEntity;
    }
}
