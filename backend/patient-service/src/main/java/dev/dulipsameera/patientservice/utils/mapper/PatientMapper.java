package dev.dulipsameera.patientservice.utils.mapper;

import dev.dulipsameera.patientservice.dto.AddressDto;
import dev.dulipsameera.patientservice.dto.PatientDto;
import dev.dulipsameera.patientservice.entity.PatientEntity;
import dev.dulipsameera.patientservice.entity.embeddable.Address;
import dev.dulipsameera.patientservice.enums.PatientStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class PatientMapper {

    public static PatientDto toDto(PatientEntity patientEntity) {

        Address address = patientEntity.getAddress();
        AddressDto addressDto = AddressDto.builder()
                .street1(address.getStreet1())
                .street2(address.getStreet2())
                .city(address.getCity())
                .district(address.getDistrict())
                .province(address.getProvince())
                .zipCode(address.getZipCode())
                .build();

        return PatientDto.builder()
                .shareId(patientEntity.getShareId())
                .firstName(patientEntity.getFirstName())
                .lastName(patientEntity.getLastName())
                .email(patientEntity.getEmail())
                .nic(patientEntity.getNic())
                .contactNo(patientEntity.getContactNo())
                .dateOfBirth(patientEntity.getDateOfBirth())
                .address(addressDto)
                .createdAt(patientEntity.getCreatedAt())
                .updatedAt(patientEntity.getUpdatedAt())
                .status(patientEntity.getStatus().name())
                .build();
    }

    public static PatientEntity toEntity(PatientDto patientDto) {
        AddressDto addressDto = patientDto.getAddress();
        Address address = Address.builder()
                .street1(addressDto.getStreet1())
                .street2(addressDto.getStreet2())
                .city(addressDto.getCity())
                .district(addressDto.getDistrict())
                .province(addressDto.getProvince())
                .zipCode(addressDto.getZipCode())
                .build();

        PatientStatusEnum patientStatus = patientDto.getStatus().equals(PatientStatusEnum.ACTIVE.name()) ? PatientStatusEnum.ACTIVE : PatientStatusEnum.DELETED;

        return PatientEntity.builder()
                .shareId(patientDto.getShareId())
                .firstName(patientDto.getFirstName())
                .lastName(patientDto.getLastName())
                .email(patientDto.getEmail())
                .nic(patientDto.getNic())
                .contactNo(patientDto.getContactNo())
                .dateOfBirth(patientDto.getDateOfBirth())
                .address(address)
                .createdAt(patientDto.getCreatedAt())
                .updatedAt(patientDto.getUpdatedAt())
                .status(patientStatus)
                .build();
    }
}
