package com.upsaude.integration.brevo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BrevoEmailResponse {
    
    @JsonProperty("messageId")
    private String messageId;
}
