package dev.dulipsameera.patientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private String shareId;
    private String firstName;
    private String lastName;
    private String email;
    private String nic;
    private String contactNo;
    private LocalDate dateOfBirth;
    private AddressDto address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;

}
