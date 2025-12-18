package com.upsaude.controller.sistema;

import com.upsaude.api.request.sistema.LGPDConsentimentoRequest;
import com.upsaude.api.response.sistema.LGPDConsentimentoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.sistema.LGPDConsentimentoService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/lgpd-consentimentos")
@Tag(name = "LGPD Consentimentos", description = "API para gerenciamento de Consentimentos LGPD")
@RequiredArgsConstructor
public class LGPDConsentimentoController {

    private final LGPDConsentimentoService service;

    @PostMapping
    @Operation(summary = "Criar consentimento LGPD", description = "Cria novo consentimento LGPD para um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consentimento LGPD criado com sucesso",
                    content = @Content(schema = @Schema(implementation = LGPDConsentimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LGPDConsentimentoResponse> criar(@Valid @RequestBody LGPDConsentimentoRequest request) {
        log.debug("REQUEST POST /v1/lgpd-consentimentos - payload: {}", request);
        try {
            LGPDConsentimentoResponse response = service.criar(request);
            log.info("Consentimento LGPD criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar consentimento LGPD — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar consentimento LGPD — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar consentimentos LGPD", description = "Retorna uma lista paginada de consentimentos LGPD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<LGPDConsentimentoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/lgpd-consentimentos - pageable: {}", pageable);
        try {
            Page<LGPDConsentimentoResponse> response = service.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar consentimentos LGPD — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter por ID", description = "Retorna consentimento LGPD específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consentimento encontrado",
                    content = @Content(schema = @Schema(implementation = LGPDConsentimentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Consentimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LGPDConsentimentoResponse> obterPorId(
            @Parameter(description = "ID do consentimento LGPD", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/lgpd-consentimentos/{}", id);
        try {
            LGPDConsentimentoResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Consentimento LGPD não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter consentimento LGPD por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obter por paciente ID", description = "Retorna consentimento LGPD de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consentimento encontrado",
                    content = @Content(schema = @Schema(implementation = LGPDConsentimentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Consentimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LGPDConsentimentoResponse> obterPorPacienteId(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId) {
        log.debug("REQUEST GET /v1/lgpd-consentimentos/paciente/{}", pacienteId);
        try {
            LGPDConsentimentoResponse response = service.obterPorPacienteId(pacienteId);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Consentimento LGPD não encontrado para paciente — pacienteId: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter consentimento LGPD por paciente ID — pacienteId: {}", pacienteId, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar consentimento LGPD", description = "Atualiza consentimento LGPD existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consentimento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LGPDConsentimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Consentimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LGPDConsentimentoResponse> atualizar(
            @Parameter(description = "ID do consentimento LGPD", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody LGPDConsentimentoRequest request) {
        log.debug("REQUEST PUT /v1/lgpd-consentimentos/{} - payload: {}", id, request);
        try {
            LGPDConsentimentoResponse response = service.atualizar(id, request);
            log.info("Consentimento LGPD atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar consentimento LGPD — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar consentimento LGPD — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir consentimento LGPD", description = "Exclui (desativa) consentimento LGPD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consentimento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consentimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do consentimento LGPD", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/lgpd-consentimentos/{}", id);
        try {
            service.excluir(id);
            log.info("Consentimento LGPD excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir consentimento LGPD — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir consentimento LGPD — ID: {}", id, ex);
            throw ex;
        }
    }
}
