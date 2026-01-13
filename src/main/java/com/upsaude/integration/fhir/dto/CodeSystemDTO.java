package com.upsaude.integration.fhir.dto;

import java.io.Serializable;
import java.util.ArrayList;
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
public class CodeSystemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String resourceType;
    private String id;
    private String url;
    private String version;
    private String name;
    private String title;
    private String status;
    private String description;
    private String publisher;
    private String date;

    private String content;
    private Integer count;
    private String hierarchyMeaning;
    private Boolean caseSensitive;
    private Boolean compositional;
    private Boolean versionNeeded;

    @Builder.Default
    private List<ConceptDTO> concept = new ArrayList<>();

    @Builder.Default
    private List<PropertyDefinitionDTO> property = new ArrayList<>();

    public int getConceptCount() {
        return concept != null ? countConceptsRecursive(concept) : 0;
    }

    private int countConceptsRecursive(List<ConceptDTO> concepts) {
        if (concepts == null)
            return 0;
        int count = concepts.size();
        for (ConceptDTO c : concepts) {
            if (c.getConcept() != null) {
                count += countConceptsRecursive(c.getConcept());
            }
        }
        return count;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PropertyDefinitionDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String code;
        private String uri;
        private String description;
        private String type;
    }
}
