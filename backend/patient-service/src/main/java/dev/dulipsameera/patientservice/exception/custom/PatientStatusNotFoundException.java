package dev.dulipsameera.patientservice.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Patient Status Not Found")
public class PatientStatusNotFoundException extends RuntimeException {
    public PatientStatusNotFoundException(String message) {
        super(message);
    }
}
