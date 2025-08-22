package dev.dulipsameera.patientservice.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Patient creation failed")
public class PatientCreationException extends RuntimeException {
    public PatientCreationException(String message) {
        super(message);
    }
    public PatientCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
