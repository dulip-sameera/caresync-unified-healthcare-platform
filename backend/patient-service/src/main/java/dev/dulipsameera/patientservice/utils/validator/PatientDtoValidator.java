package dev.dulipsameera.patientservice.utils.validator;

import dev.dulipsameera.patientservice.dto.PatientDto;
import dev.dulipsameera.patientservice.exception.custom.PatientDtoValidationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class PatientDtoValidator {

    // Regex patterns (same as before)
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(?:\\+94|0)[1-9][0-9]{8}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z ]{1,255}$");
    private static final Pattern NIC_PATTERN = Pattern.compile("^([0-9]{9}[vVxX]|[0-9]{12})$");

    public void validate(PatientDto patientDto) {
        Map<String, String> errors = new HashMap<>();

        if (patientDto.getEmail() != null && !EMAIL_PATTERN.matcher(patientDto.getEmail()).matches()) {
            errors.put("email", "Invalid email format");
        }

        if (patientDto.getContactNo() != null && !PHONE_PATTERN.matcher(patientDto.getContactNo()).matches()) {
            errors.put("contactNo", "Invalid Sri Lankan phone number");
        }

        if (patientDto.getFirstName() != null && !NAME_PATTERN.matcher(patientDto.getFirstName()).matches()) {
            errors.put("firstName", "Invalid first name (letters only, max 255 chars)");
        }

        if (patientDto.getLastName() != null && !NAME_PATTERN.matcher(patientDto.getLastName()).matches()) {
            errors.put("lastName", "Invalid last name (letters only, max 255 chars)");
        }

        if (patientDto.getNic() != null && !NIC_PATTERN.matcher(patientDto.getNic()).matches()) {
            errors.put("nic", "Invalid NIC (must be old format 9 digits + V/v/X/x or new format 12 digits)");
        }

        if (!errors.isEmpty()) {
            throw new PatientDtoValidationException(errors);
        }
    }
}
