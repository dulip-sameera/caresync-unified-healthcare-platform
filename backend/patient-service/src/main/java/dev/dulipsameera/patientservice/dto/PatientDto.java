package dev.dulipsameera.patientservice.dto;

import dev.dulipsameera.patientservice.entity.PatientStatusEntity;
import dev.dulipsameera.patientservice.entity.embedded.PatientAddress;
import dev.dulipsameera.patientservice.enums.PatientStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private UUID id;
    private String shareId;
    private String firstName;
    private String lastName;
    private String email;
    private String nic;
    private String contactNo;
    private LocalDate dateOfBirth;
    private PatientAddressDto address;
    private UUID userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
}
