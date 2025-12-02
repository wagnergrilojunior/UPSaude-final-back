package com.upsaude.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private UUID userId;
    private String email;
    private Map<String, Object> userMetadata;
    private Map<String, Object> appMetadata;
    private String role;
    
    // Informações completas do usuário no sistema
    private UsuarioSistemaInfoResponse usuarioSistema;
}

