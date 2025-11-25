package com.upsaude.integration.supabase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.config.SupabaseConfig;
import com.upsaude.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupabaseAuthService {

    private final SupabaseConfig supabaseConfig;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Autentica um usuário usando email e senha através do Supabase Auth
     * 
     * @param email Email do usuário
     * @param password Senha do usuário
     * @return Resposta de autenticação com token e informações do usuário
     * @throws UnauthorizedException se as credenciais forem inválidas
     */
    public SupabaseAuthResponse signInWithEmail(String email, String password) {
        String url = supabaseConfig.getUrl() + "/auth/v1/token?grant_type=password";
        
        // Preparar headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseConfig.getAnonKey());
        headers.set("Authorization", "Bearer " + supabaseConfig.getAnonKey());
        
        // Preparar body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);
        
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
        
        try {
            log.debug("Tentando autenticar usuário com email: {}", email);
            ResponseEntity<SupabaseAuthResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    SupabaseAuthResponse.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("Usuário autenticado com sucesso: {}", email);
                return response.getBody();
            }
            
            throw new UnauthorizedException("Falha na autenticação");
            
        } catch (HttpClientErrorException e) {
            log.error("Erro ao autenticar usuário: {}", e.getMessage());
            
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED || 
                e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                try {
                    // Tentar extrair mensagem de erro do Supabase
                    String errorBody = e.getResponseBodyAsString();
                    if (errorBody != null && errorBody.contains("message")) {
                        SupabaseAuthError error = objectMapper.readValue(
                                errorBody, 
                                SupabaseAuthError.class
                        );
                        throw new UnauthorizedException(
                                error.getMessage() != null ? 
                                error.getMessage() : "Credenciais inválidas"
                        );
                    }
                } catch (Exception parseException) {
                    log.warn("Não foi possível parsear erro do Supabase", parseException);
                }
                throw new UnauthorizedException("Credenciais inválidas");
            }
            
            throw new UnauthorizedException("Erro ao autenticar usuário: " + e.getMessage());
        } catch (Exception e) {
            log.error("Erro inesperado ao autenticar usuário", e);
            throw new UnauthorizedException("Erro ao autenticar usuário: " + e.getMessage());
        }
    }

    /**
     * Verifica e valida um token JWT do Supabase
     * 
     * @param token Token JWT para validar
     * @return Informações do usuário associado ao token
     * @throws UnauthorizedException se o token for inválido ou expirado
     */
    public SupabaseAuthResponse.User verifyToken(String token) {
        String url = supabaseConfig.getUrl() + "/auth/v1/user";
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", supabaseConfig.getAnonKey());
        headers.set("Authorization", "Bearer " + token);
        
        HttpEntity<Void> request = new HttpEntity<>(headers);
        
        try {
            log.debug("Verificando token no Supabase: {}", url);
            ResponseEntity<SupabaseAuthResponse.User> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    SupabaseAuthResponse.User.class
            );
            
            log.debug("Resposta do Supabase - Status: {}, Body presente: {}", 
                    response.getStatusCode(), response.getBody() != null);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.debug("Token válido. Usuário: {}", response.getBody().getEmail());
                return response.getBody();
            }
            
            log.warn("Token inválido - Status: {}, Body: {}", 
                    response.getStatusCode(), response.getBody());
            throw new UnauthorizedException("Token inválido");
            
        } catch (HttpClientErrorException e) {
            log.error("Erro HTTP ao verificar token - Status: {}, Body: {}", 
                    e.getStatusCode(), e.getResponseBodyAsString());
            
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED || 
                e.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new UnauthorizedException("Token inválido ou expirado");
            }
            
            throw new UnauthorizedException("Erro ao verificar token: " + e.getMessage());
        } catch (Exception e) {
            log.error("Erro inesperado ao verificar token", e);
            throw new UnauthorizedException("Erro ao verificar token: " + e.getMessage());
        }
    }

    /**
     * Obtém informações do usuário usando o token
     * 
     * @param token Token JWT do usuário
     * @return Informações do usuário
     */
    public SupabaseAuthResponse.User getUser(String token) {
        return verifyToken(token);
    }
}

