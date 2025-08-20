package dev.dulipsameera.patientservice.enums;

public enum PatientStatusEnum {
    ACTIVE(1, "ACTIVE"), DELETED(2, "DELETED");

    private Integer id;
    private String name;

    PatientStatusEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
