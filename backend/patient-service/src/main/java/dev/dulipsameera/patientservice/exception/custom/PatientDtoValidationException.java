package dev.dulipsameera.patientservice.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Getter
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "PatientDto validation failed")
public class PatientDtoValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public PatientDtoValidationException(Map<String, String> errors) {
        super("PatientDto validation failed");
        this.errors = errors;
    }

}
