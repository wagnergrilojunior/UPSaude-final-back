package com.upsaude.integration.rnds.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RndsAppointmentDTO {
    private String resourceType; // "Appointment"
    private String status; // proposed | pending | booked | arrived | fulfilled | cancelled | noshow
    private RndsPeriodDTO requestedPeriod;
    private List<RndsParticipantDTO> participant;
    private String description;
    private String created;
    private String appointmentType;
    private List<RndsReasonCodeDTO> reasonCode;
    private String comment;
    private String serviceCategory;
    private String serviceType;
    private String specialty;
    private RndsIdentifierDTO identifier;

    @Data
    @Builder
    public static class RndsPeriodDTO {
        private String start;
        private String end;
    }

    @Data
    @Builder
    public static class RndsParticipantDTO {
        private String type; // required | optional
        private String status; // accepted | declined | tentative | needs-action
        private RndsReferenceDTO actor;
    }

    @Data
    @Builder
    public static class RndsReferenceDTO {
        private String reference;
        private String display;
        private String type;
        private RndsIdentifierDTO identifier;
    }

    @Data
    @Builder
    public static class RndsReasonCodeDTO {
        private RndsCodingDTO coding;
        private String text;
    }

    @Data
    @Builder
    public static class RndsCodingDTO {
        private String system;
        private String code;
        private String display;
    }

    @Data
    @Builder
    public static class RndsIdentifierDTO {
        private String system;
        private String value;
    }
}
