package dev.dulipsameera.patientservice.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String street1;
    @Column(nullable = true)
    private String street2;
    private String city;
    private String district;
    private String province;
    private String zipCode;
}
