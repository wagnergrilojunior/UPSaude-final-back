package com.upsaude.integration.supabase;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SupabaseAuthError {
    
    private String message;
    
    @JsonProperty("error_description")
    private String errorDescription;
    
    @JsonProperty("error_code")
    private String errorCode;
    
    @JsonProperty("status_code")
    private Integer statusCode;
}

