package dev.dulipsameera.patientservice.utils.mapper;

import dev.dulipsameera.patientservice.dto.PatientAddressDto;
import dev.dulipsameera.patientservice.entity.embedded.PatientAddress;

public class PatientAddressMapper {

    public static PatientAddressDto toDto(PatientAddress patientAddress) {
        PatientAddressDto patientAddressDto = new PatientAddressDto();
        patientAddressDto.setStreet1(patientAddress.getStreet1());
        patientAddressDto.setStreet2(patientAddress.getStreet2());
        patientAddressDto.setCity(patientAddress.getCity());
        patientAddressDto.setDistrict(patientAddress.getDistrict());
        patientAddressDto.setZipCode(patientAddress.getZipCode());
        return patientAddressDto;
    }

    public static PatientAddress toEntity(PatientAddressDto patientAddressDto) {
        PatientAddress patientAddress = new PatientAddress();
        patientAddress.setStreet1(patientAddressDto.getStreet1());
        patientAddress.setStreet2(patientAddressDto.getStreet2());
        patientAddress.setCity(patientAddressDto.getCity());
        patientAddress.setDistrict(patientAddressDto.getDistrict());
        patientAddress.setZipCode(patientAddressDto.getZipCode());
        return patientAddress;
    }
}
