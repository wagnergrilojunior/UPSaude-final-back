package com.upsaude.integration.fhir.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConceptDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String display;
    private String definition;

    private List<ConceptDTO> concept;

    private List<PropertyDTO> property;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PropertyDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String code;
        private String valueCode;
        private String valueString;
        private Boolean valueBoolean;
        private Integer valueInteger;
    }
}
