package com.upsaude.integration.supabase;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.config.SupabaseConfig;
import com.upsaude.exception.UnauthorizedException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupabaseAuthService {

    private final SupabaseConfig supabaseConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; 

    public SupabaseAuthResponse signInWithEmail(String email, String password) {
        String baseUrl = supabaseConfig.getUrl();
        if (baseUrl == null || baseUrl.isEmpty()) {
            log.error("URL do Supabase não configurada!");
            throw new RuntimeException("Configuração do Supabase inválida: URL não definida");
        }
        
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        
        String url = baseUrl + "/auth/v1/token?grant_type=password";
        
        if (supabaseConfig.getAnonKey() == null || supabaseConfig.getAnonKey().isEmpty()) {
            log.error("Chave anon do Supabase não configurada!");
            throw new RuntimeException("Configuração do Supabase inválida: ANON_KEY não definida");
        }
        
        log.debug("Tentando autenticar no Supabase. URL: {}, Email: {}", url, email);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseConfig.getAnonKey());
        headers.set("Authorization", "Bearer " + supabaseConfig.getAnonKey());
        
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);
        
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
        
        try {
            log.debug("Enviando requisição de autenticação para: {}", url);
            ResponseEntity<SupabaseAuthResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    SupabaseAuthResponse.class
            );
            
            log.debug("Resposta recebida - Status: {}, Body presente: {}", 
                    response.getStatusCode(), response.getBody() != null);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("Usuário autenticado com sucesso: {}", email);
                return response.getBody();
            }
            
            log.warn("Falha na autenticação - Status: {}, Body: {}", 
                    response.getStatusCode(), response.getBody());
            throw new UnauthorizedException("Falha na autenticação");
            
        } catch (HttpServerErrorException e) {
            log.error("Erro do servidor Supabase ao autenticar usuário - Status: {}, Body: {}, URL: {}", 
                    e.getStatusCode(), e.getResponseBodyAsString(), url);
            
            String errorMessage = "Serviço de autenticação temporariamente indisponível";
            if (e.getStatusCode() == HttpStatus.BAD_GATEWAY || 
                e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE ||
                e.getStatusCode() == HttpStatus.GATEWAY_TIMEOUT) {
                errorMessage = "Serviço de autenticação temporariamente indisponível. Tente novamente em alguns instantes.";
            }
            
            throw new RuntimeException(errorMessage + " (Status: " + e.getStatusCode() + ")", e);
        } catch (HttpClientErrorException e) {
            log.error("Erro HTTP ao autenticar usuário - Status: {}, Body: {}, URL: {}", 
                    e.getStatusCode(), e.getResponseBodyAsString(), url);
            
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED || 
                e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                try {
                    String errorBody = e.getResponseBodyAsString();
                    log.debug("Corpo do erro do Supabase: {}", errorBody);
                    if (errorBody != null && errorBody.contains("message")) {
                        SupabaseAuthError error = objectMapper.readValue(
                                errorBody, 
                                SupabaseAuthError.class
                        );
                        String errorMessage = error.getMessage() != null ? 
                                error.getMessage() : "Credenciais inválidas";
                        log.warn("Erro de autenticação: {}", errorMessage);
                        throw new UnauthorizedException(errorMessage);
                    }
                } catch (Exception parseException) {
                    log.warn("Não foi possível parsear erro do Supabase: {}", 
                            parseException.getMessage(), parseException);
                }
                throw new UnauthorizedException("Credenciais inválidas");
            }
            
            log.error("Erro HTTP não tratado: {}", e.getMessage());
            throw new UnauthorizedException("Erro ao autenticar usuário: " + e.getMessage());
        } catch (org.springframework.web.client.RestClientException e) {
            log.error("Erro de conexão ao autenticar usuário no Supabase. URL: {}, Erro: {}", url, e.getMessage(), e);
            Throwable cause = e.getCause();
            if (cause != null && (cause instanceof com.fasterxml.jackson.databind.exc.MismatchedInputException ||
                                  cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException)) {
                log.error("Erro ao deserializar resposta do Supabase. Resposta pode estar em formato inesperado.");
                throw new RuntimeException("Erro ao processar resposta do serviço de autenticação: " + cause.getMessage(), e);
            }
            throw new RuntimeException("Erro ao conectar com o serviço de autenticação: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Erro inesperado ao autenticar usuário. URL: {}, Tipo: {}, Erro: {}", 
                    url, e.getClass().getName(), e.getMessage(), e);
            throw new RuntimeException("Erro inesperado ao autenticar usuário: " + e.getMessage(), e);
        }
    }

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

    public SupabaseAuthResponse.User getUser(String token) {
        return verifyToken(token);
    }

    public SupabaseAuthResponse.User signUp(String email, String password) {
        String baseUrl = supabaseConfig.getUrl();
        if (baseUrl == null || baseUrl.isEmpty()) {
            log.error("URL do Supabase não configurada!");
            throw new RuntimeException("Configuração do Supabase inválida: URL não definida");
        }
        
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        
        String url = baseUrl + "/auth/v1/admin/users";
        
        log.debug("Criando novo usuário no Supabase Auth. URL: {}, Email: {}", url, email);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseConfig.getServiceRoleKey());
        headers.set("Authorization", "Bearer " + supabaseConfig.getServiceRoleKey());
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("email_confirm", true); // Auto-confirma o email
        
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        
        try {
            log.debug("Enviando requisição de criação de usuário para: {}", url);
            ResponseEntity<SupabaseAuthResponse.User> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    SupabaseAuthResponse.User.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("Usuário criado com sucesso no Supabase Auth: {}", email);
                return response.getBody();
            }
            
            log.warn("Falha ao criar usuário - Status: {}, Body: {}", 
                    response.getStatusCode(), response.getBody());
            throw new RuntimeException("Falha ao criar usuário no Supabase Auth");
            
        } catch (HttpClientErrorException e) {
            log.error("Erro HTTP ao criar usuário - Status: {}, Body: {}", 
                    e.getStatusCode(), e.getResponseBodyAsString());
            
            String errorMessage = "Erro ao criar usuário: ";
            if (e.getResponseBodyAsString().contains("already registered")) {
                errorMessage = "Email já cadastrado";
            } else if (e.getResponseBodyAsString().contains("password")) {
                errorMessage = "Senha inválida (mínimo 6 caracteres)";
            }
            
            throw new RuntimeException(errorMessage + e.getMessage());
        } catch (Exception e) {
            log.error("Erro inesperado ao criar usuário no Supabase Auth", e);
            throw new RuntimeException("Erro ao criar usuário: " + e.getMessage(), e);
        }
    }

    public SupabaseAuthResponse.User updateUser(java.util.UUID userId, String email, String password) {
        String baseUrl = supabaseConfig.getUrl();
        if (baseUrl == null || baseUrl.isEmpty()) {
            log.error("URL do Supabase não configurada!");
            throw new RuntimeException("Configuração do Supabase inválida: URL não definida");
        }
        
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        
        String url = baseUrl + "/auth/v1/admin/users/" + userId;
        
        log.debug("Atualizando usuário no Supabase Auth. URL: {}, UserID: {}, Email: {}, Senha: {}", 
                url, userId, email, password != null ? "***" : "não fornecida");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseConfig.getServiceRoleKey());
        headers.set("Authorization", "Bearer " + supabaseConfig.getServiceRoleKey());
        
        Map<String, Object> requestBody = new HashMap<>();
        
        if (email != null && !email.trim().isEmpty()) {
            requestBody.put("email", email);
        }
        
        if (password != null && !password.trim().isEmpty()) {
            requestBody.put("password", password);
            log.debug("Nova senha será atualizada para o usuário {}", userId);
        }
        
        if (requestBody.isEmpty()) {
            log.warn("Nenhum campo para atualizar foi fornecido para o usuário: {}", userId);
            throw new RuntimeException("Nenhum dado fornecido para atualização");
        }
        
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        
        try {
            log.debug("Enviando requisição de atualização de usuário para: {}", url);
            ResponseEntity<SupabaseAuthResponse.User> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    request,
                    SupabaseAuthResponse.User.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("Usuário atualizado com sucesso no Supabase Auth. UserID: {}", userId);
                return response.getBody();
            }
            
            log.warn("Falha ao atualizar usuário - Status: {}, Body: {}", 
                    response.getStatusCode(), response.getBody());
            throw new RuntimeException("Falha ao atualizar usuário no Supabase Auth");
            
        } catch (HttpClientErrorException e) {
            log.error("Erro HTTP ao atualizar usuário - Status: {}, Body: {}", 
                    e.getStatusCode(), e.getResponseBodyAsString());
            
            String errorMessage = "Erro ao atualizar usuário: ";
            if (e.getResponseBodyAsString().contains("already registered")) {
                errorMessage = "Email já está em uso por outro usuário";
            } else if (e.getResponseBodyAsString().contains("password")) {
                errorMessage = "Senha inválida (mínimo 6 caracteres)";
            }
            
            throw new RuntimeException(errorMessage + e.getMessage());
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar usuário no Supabase Auth", e);
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }
    
    public SupabaseAuthResponse.User updateUser(java.util.UUID userId, String email) {
        return updateUser(userId, email, null);
    }

    public void deleteUser(java.util.UUID userId) {
        String baseUrl = supabaseConfig.getUrl();
        if (baseUrl == null || baseUrl.isEmpty()) {
            log.error("URL do Supabase não configurada!");
            throw new RuntimeException("Configuração do Supabase inválida: URL não definida");
        }
        
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        
        String url = baseUrl + "/auth/v1/admin/users/" + userId;
        
        log.debug("Deletando usuário no Supabase Auth. URL: {}, UserID: {}", url, userId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", supabaseConfig.getServiceRoleKey());
        headers.set("Authorization", "Bearer " + supabaseConfig.getServiceRoleKey());
        
        HttpEntity<Void> request = new HttpEntity<>(headers);
        
        try {
            log.debug("Enviando requisição de exclusão de usuário para: {}", url);
            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    request,
                    Void.class
            );
            
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Usuário deletado com sucesso no Supabase Auth: {}", userId);
                return;
            }
            
            log.warn("Falha ao deletar usuário - Status: {}", response.getStatusCode());
            throw new RuntimeException("Falha ao deletar usuário no Supabase Auth");
            
        } catch (HttpClientErrorException e) {
            log.error("Erro HTTP ao deletar usuário - Status: {}, Body: {}", 
                    e.getStatusCode(), e.getResponseBodyAsString());
            
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.warn("Usuário não encontrado no Supabase Auth: {}", userId);
                return;
            }
            
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        } catch (Exception e) {
            log.error("Erro inesperado ao deletar usuário no Supabase Auth", e);
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }

    public SupabaseAuthResponse.User getUserById(java.util.UUID userId) {
        String url = supabaseConfig.getUrl() + "/auth/v1/admin/users/" + userId;
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", supabaseConfig.getServiceRoleKey());
        headers.set("Authorization", "Bearer " + supabaseConfig.getServiceRoleKey());
        
        HttpEntity<Void> request = new HttpEntity<>(headers);
        
        try {
            log.debug("Buscando usuário no Supabase Auth por ID: {}", userId);
            ResponseEntity<SupabaseAuthResponse.User> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    SupabaseAuthResponse.User.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.debug("Usuário encontrado: {}", response.getBody().getEmail());
                return response.getBody();
            }
            
            log.warn("Usuário não encontrado - Status: {}", response.getStatusCode());
            return null;
            
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == org.springframework.http.HttpStatus.NOT_FOUND) {
                log.warn("Usuário não encontrado no Supabase Auth: {}", userId);
                return null;
            }
            log.error("Erro HTTP ao buscar usuário - Status: {}, Body: {}", 
                    e.getStatusCode(), e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            log.error("Erro ao buscar usuário por ID", e);
            return null;
        }
    }
}

