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
public class ValueSetDTO implements Serializable {

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

    private ComposeDTO compose;
    private ExpansionDTO expansion;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ComposeDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private Boolean inactive;
        @Builder.Default
        private List<IncludeDTO> include = new ArrayList<>();
        @Builder.Default
        private List<IncludeDTO> exclude = new ArrayList<>();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IncludeDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String system;
        private String version;
        @Builder.Default
        private List<ConceptReferenceDTO> concept = new ArrayList<>();
        @Builder.Default
        private List<FilterDTO> filter = new ArrayList<>();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ConceptReferenceDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String code;
        private String display;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FilterDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String property;
        private String op;
        private String value;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExpansionDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String identifier;
        private String timestamp;
        private Integer total;
        private Integer offset;
        @Builder.Default
        private List<ContainsDTO> contains = new ArrayList<>();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ContainsDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String system;
        private String code;
        private String display;
        private Boolean abstract_;
        private Boolean inactive;
        @Builder.Default
        private List<ContainsDTO> contains = new ArrayList<>();
    }
}
