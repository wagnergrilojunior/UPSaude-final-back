package com.upsaude.integration.fhir.dto;

import java.io.Serializable;

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
public class FhirResourceDTO implements Serializable {

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
}
