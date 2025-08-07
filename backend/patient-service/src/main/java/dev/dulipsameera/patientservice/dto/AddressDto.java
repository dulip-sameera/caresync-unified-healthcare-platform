package dev.dulipsameera.patientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String street1;
    private String street2;
    private String city;
    private String district;
    private String province;
    private String zipCode;
}
