package dev.dulipsameera.patientservice.entity.embedded;

import jakarta.persistence.Embeddable;
import org.antlr.v4.runtime.misc.NotNull;

@Embeddable
public class PatientAddress {

    @NotNull
    private String street1;
    private String street2;
    @NotNull
    private String city;
    @NotNull
    private String district;
    @NotNull
    private String province;
    @NotNull
    private String zipCode;
}
