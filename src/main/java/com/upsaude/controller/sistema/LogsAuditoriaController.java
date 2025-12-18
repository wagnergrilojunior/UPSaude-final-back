package com.upsaude.controller.sistema;

import com.upsaude.api.request.sistema.LogsAuditoriaRequest;
import com.upsaude.api.response.sistema.LogsAuditoriaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.sistema.LogsAuditoriaService;
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
@RequestMapping("/v1/logs-auditoria")
@Tag(name = "Logs de Auditoria", description = "API para gerenciamento de Logs de Auditoria")
@RequiredArgsConstructor
public class LogsAuditoriaController {

    private final LogsAuditoriaService logsAuditoriaService;

    @PostMapping
    @Operation(summary = "Criar novo log de auditoria", description = "Cria um novo log de auditoria no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Log de auditoria criado com sucesso",
                    content = @Content(schema = @Schema(implementation = LogsAuditoriaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LogsAuditoriaResponse> criar(@Valid @RequestBody LogsAuditoriaRequest request) {
        log.debug("REQUEST POST /v1/logs-auditoria - payload: {}", request);
        try {
            LogsAuditoriaResponse response = logsAuditoriaService.criar(request);
            log.info("Log de auditoria criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar log de auditoria — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar log de auditoria — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar logs de auditoria", description = "Retorna uma lista paginada de logs de auditoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de logs de auditoria retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<LogsAuditoriaResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/logs-auditoria - pageable: {}", pageable);
        try {
            Page<LogsAuditoriaResponse> response = logsAuditoriaService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar logs de auditoria — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter log de auditoria por ID", description = "Retorna um log de auditoria específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Log de auditoria encontrado",
                    content = @Content(schema = @Schema(implementation = LogsAuditoriaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Log de auditoria não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LogsAuditoriaResponse> obterPorId(
            @Parameter(description = "ID do log de auditoria", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/logs-auditoria/{}", id);
        try {
            LogsAuditoriaResponse response = logsAuditoriaService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Log de auditoria não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter log de auditoria por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar log de auditoria", description = "Atualiza um log de auditoria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Log de auditoria atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LogsAuditoriaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Log de auditoria não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LogsAuditoriaResponse> atualizar(
            @Parameter(description = "ID do log de auditoria", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody LogsAuditoriaRequest request) {
        log.debug("REQUEST PUT /v1/logs-auditoria/{} - payload: {}", id, request);
        try {
            LogsAuditoriaResponse response = logsAuditoriaService.atualizar(id, request);
            log.info("Log de auditoria atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar log de auditoria — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar log de auditoria — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir log de auditoria", description = "Exclui (desativa) um log de auditoria do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Log de auditoria excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Log de auditoria não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do log de auditoria", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/logs-auditoria/{}", id);
        try {
            logsAuditoriaService.excluir(id);
            log.info("Log de auditoria excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir log de auditoria — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir log de auditoria — ID: {}", id, ex);
            throw ex;
        }
    }
}
