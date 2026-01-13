package com.upsaude.entity.vacinacao;

public enum StatusAplicacao {
    COMPLETED("completed"),
    ENTERED_IN_ERROR("entered-in-error"),
    NOT_DONE("not-done");

    private final String fhirCode;

    StatusAplicacao(String fhirCode) {
        this.fhirCode = fhirCode;
    }

    public String getFhirCode() {
        return fhirCode;
    }

    public static StatusAplicacao fromFhirCode(String code) {
        for (StatusAplicacao status : values()) {
            if (status.fhirCode.equals(code)) {
                return status;
            }
        }
        return COMPLETED;
    }
}
