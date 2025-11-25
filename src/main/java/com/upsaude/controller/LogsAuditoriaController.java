package com.upsaude.controller;

import com.upsaude.api.request.LogsAuditoriaRequest;
import com.upsaude.api.response.LogsAuditoriaResponse;
import com.upsaude.service.LogsAuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a Logs de Auditoria.
 *
 * @author UPSaúde
 */
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
        LogsAuditoriaResponse response = logsAuditoriaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
        Page<LogsAuditoriaResponse> response = logsAuditoriaService.listar(pageable);
        return ResponseEntity.ok(response);
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
        LogsAuditoriaResponse response = logsAuditoriaService.obterPorId(id);
        return ResponseEntity.ok(response);
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
        LogsAuditoriaResponse response = logsAuditoriaService.atualizar(id, request);
        return ResponseEntity.ok(response);
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
        logsAuditoriaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

