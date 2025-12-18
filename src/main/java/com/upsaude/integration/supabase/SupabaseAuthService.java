package com.upsaude.integration.supabase;
import com.upsaude.entity.sistema.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.config.SupabaseConfig;
import com.upsaude.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; // Usa @Primary do supabaseObjectMapper

    /**
     * Autentica um usuário usando email e senha através do Supabase Auth
     * 
     * @param email Email do usuário
     * @param password Senha do usuário
     * @return Resposta de autenticação com token e informações do usuário
     * @throws UnauthorizedException se as credenciais forem inválidas
     */
    public SupabaseAuthResponse signInWithEmail(String email, String password) {
        // Garantir que a URL não tenha barra dupla
        String baseUrl = supabaseConfig.getUrl();
        if (baseUrl == null || baseUrl.isEmpty()) {
            log.error("URL do Supabase não configurada!");
            throw new RuntimeException("Configuração do Supabase inválida: URL não definida");
        }
        
        // Remover barra final se existir
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        
        String url = baseUrl + "/auth/v1/token?grant_type=password";
        
        // Validar chaves do Supabase
        if (supabaseConfig.getAnonKey() == null || supabaseConfig.getAnonKey().isEmpty()) {
            log.error("Chave anon do Supabase não configurada!");
            throw new RuntimeException("Configuração do Supabase inválida: ANON_KEY não definida");
        }
        
        log.debug("Tentando autenticar no Supabase. URL: {}, Email: {}", url, email);
        
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
            
        } catch (HttpClientErrorException e) {
            log.error("Erro HTTP ao autenticar usuário - Status: {}, Body: {}, URL: {}", 
                    e.getStatusCode(), e.getResponseBodyAsString(), url);
            
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED || 
                e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                try {
                    // Tentar extrair mensagem de erro do Supabase
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
            // Verificar se é erro de deserialização
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

    /**
     * Cria um novo usuário no Supabase Auth usando service_role key (admin).
     * Este método permite criar usuários sem necessidade de confirmação de email.
     *
     * @param email Email do usuário
     * @param password Senha do usuário
     * @return Informações do usuário criado
     * @throws RuntimeException se houver erro na criação
     */
    public SupabaseAuthResponse.User signUp(String email, String password) {
        String baseUrl = supabaseConfig.getUrl();
        if (baseUrl == null || baseUrl.isEmpty()) {
            log.error("URL do Supabase não configurada!");
            throw new RuntimeException("Configuração do Supabase inválida: URL não definida");
        }
        
        // Remover barra final se existir
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        
        String url = baseUrl + "/auth/v1/admin/users";
        
        log.debug("Criando novo usuário no Supabase Auth. URL: {}, Email: {}", url, email);
        
        // Preparar headers com service_role key (permissão admin)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseConfig.getServiceRoleKey());
        headers.set("Authorization", "Bearer " + supabaseConfig.getServiceRoleKey());
        
        // Preparar body
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

    /**
     * Atualiza um usuário no Supabase Auth usando service_role key (admin).
     * Permite atualizar email e senha (ambos opcionais).
     *
     * @param userId ID do usuário
     * @param email Novo email (opcional, pode ser null)
     * @param password Nova senha (opcional, pode ser null)
     * @return Informações do usuário atualizado
     * @throws RuntimeException se houver erro na atualização
     */
    public SupabaseAuthResponse.User updateUser(java.util.UUID userId, String email, String password) {
        String baseUrl = supabaseConfig.getUrl();
        if (baseUrl == null || baseUrl.isEmpty()) {
            log.error("URL do Supabase não configurada!");
            throw new RuntimeException("Configuração do Supabase inválida: URL não definida");
        }
        
        // Remover barra final se existir
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        
        String url = baseUrl + "/auth/v1/admin/users/" + userId;
        
        log.debug("Atualizando usuário no Supabase Auth. URL: {}, UserID: {}, Email: {}, Senha: {}", 
                url, userId, email, password != null ? "***" : "não fornecida");
        
        // Preparar headers com service_role key (permissão admin)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseConfig.getServiceRoleKey());
        headers.set("Authorization", "Bearer " + supabaseConfig.getServiceRoleKey());
        
        // Preparar body - incluir apenas os campos fornecidos
        Map<String, Object> requestBody = new HashMap<>();
        
        if (email != null && !email.trim().isEmpty()) {
            requestBody.put("email", email);
        }
        
        if (password != null && !password.trim().isEmpty()) {
            requestBody.put("password", password);
            log.debug("Nova senha será atualizada para o usuário {}", userId);
        }
        
        // Se nenhum campo foi fornecido, não faz sentido atualizar
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
    
    /**
     * Atualiza um usuário no Supabase Auth usando service_role key (admin).
     * Sobrecarga do método para manter compatibilidade com código existente.
     *
     * @param userId ID do usuário
     * @param email Novo email
     * @return Informações do usuário atualizado
     * @throws RuntimeException se houver erro na atualização
     */
    public SupabaseAuthResponse.User updateUser(java.util.UUID userId, String email) {
        return updateUser(userId, email, null);
    }

    /**
     * Deleta um usuário no Supabase Auth usando service_role key (admin).
     * Remove o usuário permanentemente do sistema de autenticação.
     *
     * @param userId ID do usuário a ser deletado
     * @throws RuntimeException se houver erro na exclusão
     */
    public void deleteUser(java.util.UUID userId) {
        String baseUrl = supabaseConfig.getUrl();
        if (baseUrl == null || baseUrl.isEmpty()) {
            log.error("URL do Supabase não configurada!");
            throw new RuntimeException("Configuração do Supabase inválida: URL não definida");
        }
        
        // Remover barra final se existir
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        
        String url = baseUrl + "/auth/v1/admin/users/" + userId;
        
        log.debug("Deletando usuário no Supabase Auth. URL: {}, UserID: {}", url, userId);
        
        // Preparar headers com service_role key (permissão admin)
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
                return; // Considera como sucesso se já foi deletado
            }
            
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        } catch (Exception e) {
            log.error("Erro inesperado ao deletar usuário no Supabase Auth", e);
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }

    /**
     * Busca um usuário no Supabase Auth pelo ID usando service_role key.
     * Útil para buscar email quando temos apenas o userId.
     *
     * @param userId ID do usuário no Supabase Auth
     * @return Informações do usuário ou null se não encontrado
     */
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

