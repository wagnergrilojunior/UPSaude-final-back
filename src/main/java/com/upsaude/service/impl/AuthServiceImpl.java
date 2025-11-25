package com.upsaude.service.impl;

import com.upsaude.api.request.LoginRequest;
import com.upsaude.api.response.LoginResponse;
import com.upsaude.integration.supabase.SupabaseAuthResponse;
import com.upsaude.integration.supabase.SupabaseAuthService;
import com.upsaude.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SupabaseAuthService supabaseAuthService;

    @Override
    public LoginResponse login(LoginRequest request) {
        log.debug("Iniciando processo de login para email: {}", request.getEmail());
        
        // Chama o serviço do Supabase para autenticar
        SupabaseAuthResponse authResponse = supabaseAuthService.signInWithEmail(
                request.getEmail(), 
                request.getPassword()
        );
        
        // Converte a resposta do Supabase para o formato esperado pela API
        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(authResponse.getAccessToken())
                .refreshToken(authResponse.getRefreshToken())
                .tokenType(authResponse.getTokenType() != null ? authResponse.getTokenType() : "Bearer")
                .expiresIn(authResponse.getExpiresIn())
                .userId(authResponse.getUser() != null ? authResponse.getUser().getId() : null)
                .email(authResponse.getUser() != null ? authResponse.getUser().getEmail() : null)
                .userMetadata(authResponse.getUser() != null ? authResponse.getUser().getUserMetadata() : new HashMap<>())
                .appMetadata(authResponse.getUser() != null ? authResponse.getUser().getAppMetadata() : new HashMap<>())
                .role(authResponse.getUser() != null ? authResponse.getUser().getRole() : null)
                .build();
        
        log.info("Login realizado com sucesso para email: {}", request.getEmail());
        
        return loginResponse;
    }
}

