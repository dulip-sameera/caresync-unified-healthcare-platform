package dev.dulipsameera.patientservice.dto;

import dev.dulipsameera.patientservice.entity.embeddable.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
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
    private Address address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;

}
