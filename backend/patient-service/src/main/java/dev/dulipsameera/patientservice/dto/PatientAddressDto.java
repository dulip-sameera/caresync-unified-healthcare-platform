package dev.dulipsameera.patientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientAddressDto {
    private String street1;
    private String street2;
    private String city;
    private String district;
    private String province;
    private String zipCode;
}
