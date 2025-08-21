package dev.dulipsameera.patientservice.utils.generator;

import dev.dulipsameera.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
public class PatientShareIdGenerator {
    private final PatientRepository patientRepository;

    public PatientShareIdGenerator(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public String generateShareId() {
        String year = String.valueOf(Year.now().getValue()).substring(2); // "25"
        String prefix = "PAT-" + year;

        // Fetch max shareId from DB
        String maxShareId = patientRepository.findMaxShareIdStartingWith(prefix);

        long sequence = 1;
        if (maxShareId != null && maxShareId.length() >= 8) {
            String lastHex = maxShareId.substring(maxShareId.length() - 6);
            sequence = Long.parseLong(lastHex, 16) + 1;
        }

        if (sequence > 0xFFFFFF) {
            throw new IllegalStateException("Maximum patients reached for this year");
        }

        String hexSequence = String.format("%06X", sequence);
        return prefix + hexSequence;
    }
}
