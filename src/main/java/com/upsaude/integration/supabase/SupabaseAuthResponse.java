package com.upsaude.integration.supabase;
import com.upsaude.entity.sistema.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class SupabaseAuthResponse {
    
    @JsonProperty("access_token")
    private String accessToken;
    
    @JsonProperty("refresh_token")
    private String refreshToken;
    
    @JsonProperty("token_type")
    private String tokenType;
    
    @JsonProperty("expires_in")
    private Long expiresIn;
    
    private User user;
    
    @Data
    public static class User {
        private UUID id;
        private String email;
        
        @JsonProperty("user_metadata")
        private Map<String, Object> userMetadata;
        
        @JsonProperty("app_metadata")
        private Map<String, Object> appMetadata;
        
        @JsonProperty("role")
        private String role;
        
        @JsonProperty("aud")
        private String aud;
        
        @JsonProperty("created_at")
        private String createdAt;
        
        @JsonProperty("updated_at")
        private String updatedAt;
    }
}

