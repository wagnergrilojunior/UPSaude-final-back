package com.upsaude.integration.rnds.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RndsEncounterDTO {
    private String resourceType; // "Encounter"
    private String status; // planned | arrived | triaged | in-progress | onleave | finished | cancelled
    private RndsCodingDTO class_; // Use underscore to avoid reserved keyword
    private List<RndsCodingDTO> type;
    private RndsCodingDTO serviceType;
    private RndsCodingDTO priority;
    private RndsReferenceDTO subject;
    private List<RndsParticipantDTO> participant;
    private RndsPeriodDTO period;
    private List<RndsReasonCodeDTO> reasonCode;
    private List<RndsDiagnosisDTO> diagnosis;
    private RndsHospitalizationDTO hospitalization;
    private List<RndsLocationDTO> location;
    private RndsReferenceDTO serviceProvider;

    @Data
    @Builder
    public static class RndsCodingDTO {
        private String system;
        private String code;
        private String display;
    }

    @Data
    @Builder
    public static class RndsReferenceDTO {
        private String reference;
        private String display;
        private RndsIdentifierDTO identifier;
    }

    @Data
    @Builder
    public static class RndsIdentifierDTO {
        private String system;
        private String value;
    }

    @Data
    @Builder
    public static class RndsParticipantDTO {
        private List<RndsCodingDTO> type;
        private RndsPeriodDTO period;
        private RndsReferenceDTO individual;
    }

    @Data
    @Builder
    public static class RndsPeriodDTO {
        private String start;
        private String end;
    }

    @Data
    @Builder
    public static class RndsReasonCodeDTO {
        private List<RndsCodingDTO> coding;
        private String text;
    }

    @Data
    @Builder
    public static class RndsDiagnosisDTO {
        private RndsReferenceDTO condition;
        private RndsCodingDTO use;
        private Integer rank;
    }

    @Data
    @Builder
    public static class RndsHospitalizationDTO {
        private RndsIdentifierDTO preAdmissionIdentifier;
        private RndsReferenceDTO origin;
        private RndsCodingDTO admitSource;
        private RndsCodingDTO reAdmission;
        private List<RndsCodingDTO> dietPreference;
        private List<RndsCodingDTO> specialCourtesy;
        private List<RndsCodingDTO> specialArrangement;
        private RndsReferenceDTO destination;
        private RndsCodingDTO dischargeDisposition;
    }

    @Data
    @Builder
    public static class RndsLocationDTO {
        private RndsReferenceDTO location;
        private String status; // planned | active | reserved | completed
        private RndsCodingDTO physicalType;
        private RndsPeriodDTO period;
    }
}
