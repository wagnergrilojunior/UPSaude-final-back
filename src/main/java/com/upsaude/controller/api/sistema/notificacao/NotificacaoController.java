package com.upsaude.controller.api.sistema.notificacao;

import com.upsaude.api.request.sistema.notificacao.NotificacaoRequest;
import com.upsaude.api.request.sistema.notificacao.TesteEmailBrevoRequest;
import com.upsaude.api.response.sistema.notificacao.NotificacaoResponse;
import com.upsaude.api.response.sistema.notificacao.TesteEmailBrevoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.config.BrevoConfig;
import com.upsaude.integration.brevo.BrevoEmailClient;
import com.upsaude.integration.brevo.exception.BrevoException;
import com.upsaude.service.api.sistema.notificacao.NotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/notificacoes")
@Tag(name = "Notificações", description = "API para gerenciamento de notificações")
@RequiredArgsConstructor
@Slf4j
public class NotificacaoController {

    private final NotificacaoService service;
    private final BrevoEmailClient brevoEmailClient;
    private final BrevoConfig brevoConfig;

    @PostMapping
    @Operation(summary = "Criar notificação", description = "Cria uma nova notificação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notificação criada com sucesso",
            content = @Content(schema = @Schema(implementation = NotificacaoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<NotificacaoResponse> criar(@Valid @RequestBody NotificacaoRequest request) {
        log.debug("REQUEST POST /v1/notificacoes - payload: {}", request);
        try {
            NotificacaoResponse response = service.criar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar notificação — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar notificações", description = "Retorna uma lista paginada de notificações")
    public ResponseEntity<Page<NotificacaoResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable,
        @RequestParam(required = false) UUID estabelecimentoId,
        @RequestParam(required = false) UUID pacienteId,
        @RequestParam(required = false) UUID profissionalId,
        @RequestParam(required = false) UUID agendamentoId,
        @RequestParam(required = false) String statusEnvio,
        @RequestParam(required = false) OffsetDateTime inicio,
        @RequestParam(required = false) OffsetDateTime fim,
        @RequestParam(required = false) Boolean usarPrevista) {
        log.debug("REQUEST GET /v1/notificacoes - pageable: {}, estabelecimentoId: {}, pacienteId: {}, profissionalId: {}, agendamentoId: {}, statusEnvio: {}, inicio: {}, fim: {}, usarPrevista: {}",
            pageable, estabelecimentoId, pacienteId, profissionalId, agendamentoId, statusEnvio, inicio, fim, usarPrevista);
        Page<NotificacaoResponse> response = service.listar(pageable, estabelecimentoId, pacienteId, profissionalId, agendamentoId, statusEnvio, inicio, fim, usarPrevista);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter notificação por ID", description = "Retorna uma notificação pelo ID")
    public ResponseEntity<NotificacaoResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/notificacoes/{}", id);
        try {
            NotificacaoResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Notificação não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar notificação", description = "Atualiza uma notificação existente")
    public ResponseEntity<NotificacaoResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody NotificacaoRequest request) {
        log.debug("REQUEST PUT /v1/notificacoes/{} - payload: {}", id, request);
        NotificacaoResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir notificação", description = "Remove permanentemente uma notificação do banco de dados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Notificação excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Notificação não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/notificacoes/{}", id);
        try {
            service.excluir(id);
            log.info("Notificação excluída permanentemente com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Notificação não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir notificação — ID: {}", id, ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar notificação", description = "Inativa uma notificação no sistema alterando seu status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Notificação inativada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Notificação não encontrada"),
        @ApiResponse(responseCode = "400", description = "Notificação já está inativa"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(@PathVariable UUID id) {
        log.debug("REQUEST PATCH /v1/notificacoes/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Notificação inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Notificação não encontrada para inativação — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar notificação — ID: {}", id, ex);
            throw ex;
        }
    }

    @PostMapping("/teste-email-brevo")
    @Operation(
        summary = "Teste de envio de email via Brevo",
        description = "Endpoint de teste que envia um email imediatamente usando o template ID 5 do Brevo. " +
                     "Útil para testar a integração com o Brevo sem passar pelo dispatcher de notificações."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email enviado com sucesso",
            content = @Content(schema = @Schema(implementation = TesteEmailBrevoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro ao enviar email")
    })
    public ResponseEntity<TesteEmailBrevoResponse> testeEmailBrevo(
            @RequestBody(required = false) TesteEmailBrevoRequest request) {
        
        log.info("REQUEST POST /v1/notificacoes/teste-email-brevo - email: {}", 
                request != null ? request.getEmail() : "não informado");
        
        try {
            
            String email = (request != null && request.getEmail() != null && !request.getEmail().trim().isEmpty())
                    ? request.getEmail()
                    : "wagner.grilo85@gmail.com";
            
            String nome = (request != null && request.getNome() != null) 
                    ? request.getNome() 
                    : "Teste UPSaude";
            
            
            Integer templateId = 5;
            
            
            Map<String, Object> params = new HashMap<>();
            if (request != null && request.getParametros() != null) {
                params.putAll(request.getParametros());
            } else {
                
                params.put("nome", nome);
                params.put("email", email);
                params.put("dataHora", OffsetDateTime.now().toString());
                params.put("mensagem", "Este é um email de teste do sistema UPSaude via Brevo.");
            }
            
            
            String messageId = brevoEmailClient.sendTemplateEmail(
                    templateId,
                    email,
                    nome,
                    params,
                    BrevoEmailClient.SenderType.NOREPLY
            );
            
            log.info("Email de teste enviado com sucesso via Brevo. MessageId: {}, Email: {}", messageId, email);
            
            TesteEmailBrevoResponse response = TesteEmailBrevoResponse.builder()
                    .sucesso(true)
                    .messageId(messageId)
                    .mensagem("Email enviado com sucesso via Brevo usando template ID " + templateId)
                    .erro(null)
                    .build();
            
            return ResponseEntity.ok(response);
            
        } catch (BrevoException e) {
            log.error("Erro ao enviar email de teste via Brevo", e);
            TesteEmailBrevoResponse response = TesteEmailBrevoResponse.builder()
                    .sucesso(false)
                    .messageId(null)
                    .mensagem("Erro ao enviar email via Brevo")
                    .erro(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            
        } catch (Exception e) {
            log.error("Erro inesperado ao enviar email de teste", e);
            TesteEmailBrevoResponse response = TesteEmailBrevoResponse.builder()
                    .sucesso(false)
                    .messageId(null)
                    .mensagem("Erro inesperado ao enviar email")
                    .erro(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/teste-email-brevo/config")
    @Operation(
        summary = "Verificar configuração do Brevo",
        description = "Endpoint de diagnóstico que mostra a configuração atual do Brevo (sem enviar email)"
    )
    public ResponseEntity<Map<String, Object>> verificarConfigBrevo() {
        Map<String, Object> config = new HashMap<>();
        
        try {
            String apiKey = brevoConfig.getApiKey();
            
            config.put("enabled", brevoConfig.getEnabled());
            config.put("apiKeyPresent", apiKey != null && !apiKey.trim().isEmpty());
            config.put("apiKeyLength", apiKey != null ? apiKey.length() : 0);
            config.put("apiKeyPrefix", apiKey != null && apiKey.length() > 8 ? apiKey.substring(0, 8) : "N/A");
            config.put("apiKeySuffix", apiKey != null && apiKey.length() > 4 ? apiKey.substring(apiKey.length() - 4) : "N/A");
            config.put("apiKeyFormatValid", apiKey != null && (apiKey.startsWith("xkeysib-") || apiKey.startsWith("xsmtpsib-")));
            config.put("baseUrl", brevoConfig.getBaseUrl());
            config.put("message", "Configuração carregada com sucesso");
            
            if (apiKey == null || apiKey.trim().isEmpty()) {
                config.put("warning", "Chave API não está configurada!");
            } else if (!apiKey.startsWith("xkeysib-") && !apiKey.startsWith("xsmtpsib-")) {
                config.put("warning", "Chave API não tem formato esperado (deve começar com 'xkeysib-' ou 'xsmtpsib-')");
            }
        } catch (Exception e) {
            config.put("enabled", false);
            config.put("error", "Erro ao verificar configuração: " + e.getMessage());
            log.error("Erro ao verificar configuração do Brevo", e);
        }
        
        return ResponseEntity.ok(config);
    }

    @GetMapping("/teste-email-brevo/validar-chave")
    @Operation(
        summary = "Validar chave API do Brevo",
        description = "Testa a chave API fazendo uma chamada simples ao Brevo (/account) para verificar se está habilitada"
    )
    public ResponseEntity<Map<String, Object>> validarChaveBrevo() {
        Map<String, Object> resultado = new HashMap<>();
        
        try {
            String apiKey = brevoConfig.getApiKey();
            if (apiKey == null || apiKey.trim().isEmpty()) {
                resultado.put("valido", false);
                resultado.put("erro", "Chave API não configurada");
                return ResponseEntity.ok(resultado);
            }
            
            apiKey = apiKey.trim();
            
            
            String url = brevoConfig.getBaseUrl() + "/account";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", apiKey);
            
            HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
            
            RestTemplate restTemplate = new RestTemplate();
            try {
                ParameterizedTypeReference<Map<String, Object>> responseType = 
                        new ParameterizedTypeReference<Map<String, Object>>() {};
                
                ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        httpEntity,
                        responseType
                );
                
                if (response.getStatusCode().is2xxSuccessful()) {
                    resultado.put("valido", true);
                    resultado.put("mensagem", "Chave API válida e habilitada");
                    Map<String, Object> accountData = response.getBody();
                    if (accountData != null) {
                        resultado.put("email", accountData.get("email"));
                        resultado.put("firstName", accountData.get("firstName"));
                        resultado.put("lastName", accountData.get("lastName"));
                        resultado.put("companyName", accountData.get("companyName"));
                    }
                } else {
                    resultado.put("valido", false);
                    resultado.put("erro", "Status: " + response.getStatusCode());
                }
            } catch (HttpClientErrorException e2) {
                resultado.put("valido", false);
                resultado.put("statusCode", e2.getStatusCode().value());
                resultado.put("erro", e2.getResponseBodyAsString());
                
                if (e2.getStatusCode().value() == 401) {
                    resultado.put("detalhes", "A chave API não está habilitada ou não tem permissões. " +
                            "Possíveis causas:\n" +
                            "1. A chave foi desabilitada no painel do Brevo\n" +
                            "2. Bloqueio de IP - seu IP não está autorizado\n" +
                            "3. A chave não tem permissões de 'Send emails'\n" +
                            "4. A chave foi revogada ou expirou\n\n" +
                            "Solução: Acesse https://app.brevo.com/settings/keys/api e:\n" +
                            "- Verifique se a chave está 'Active'\n" +
                            "- Verifique as permissões da chave\n" +
                            "- Vá em Settings → Security → Authorized IPs e autorize seu IP\n" +
                            "- Ou gere uma nova chave com permissões corretas");
                }
            }
        } catch (Exception e) {
            resultado.put("valido", false);
            resultado.put("erro", "Erro ao validar chave: " + e.getMessage());
            log.error("Erro ao validar chave do Brevo", e);
        }
        
        return ResponseEntity.ok(resultado);
    }
}

