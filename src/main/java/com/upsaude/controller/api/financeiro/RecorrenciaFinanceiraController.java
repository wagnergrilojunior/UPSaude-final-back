package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.RecorrenciaFinanceiraRequest;
import com.upsaude.api.response.financeiro.RecorrenciaFinanceiraResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.RecorrenciaFinanceiraService;
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

@RestController
@RequestMapping("/v1/financeiro/recorrencias")
@Tag(name = "Financeiro - Recorrências", description = "API para gerenciamento de Recorrência Financeira")
@RequiredArgsConstructor
@Slf4j
public class RecorrenciaFinanceiraController {

    private final RecorrenciaFinanceiraService service;

    @PostMapping
    @Operation(summary = "Criar recorrência", description = "Cria uma nova recorrência financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recorrência criada com sucesso",
                    content = @Content(schema = @Schema(implementation = RecorrenciaFinanceiraResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<RecorrenciaFinanceiraResponse> criar(@Valid @RequestBody RecorrenciaFinanceiraRequest request) {
        log.debug("REQUEST POST /v1/financeiro/recorrencias - payload: {}", request);
        try {
            RecorrenciaFinanceiraResponse response = service.criar(request);
            log.info("Recorrência financeira criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar recorrência financeira — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar recorrência financeira — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar recorrências", description = "Retorna uma lista paginada de recorrências financeiras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<RecorrenciaFinanceiraResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/recorrencias - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar recorrências — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter recorrência por ID", description = "Retorna uma recorrência financeira específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recorrência encontrada",
                    content = @Content(schema = @Schema(implementation = RecorrenciaFinanceiraResponse.class))),
            @ApiResponse(responseCode = "404", description = "Recorrência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<RecorrenciaFinanceiraResponse> obterPorId(
            @Parameter(description = "ID da recorrência", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/recorrencias/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Recorrência não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter recorrência financeira — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar recorrência", description = "Atualiza uma recorrência financeira existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recorrência atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = RecorrenciaFinanceiraResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Recorrência não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<RecorrenciaFinanceiraResponse> atualizar(
            @Parameter(description = "ID da recorrência", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody RecorrenciaFinanceiraRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/recorrencias/{} - payload: {}", id, request);
        try {
            RecorrenciaFinanceiraResponse response = service.atualizar(id, request);
            log.info("Recorrência financeira atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar recorrência — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar recorrência — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir recorrência", description = "Exclui uma recorrência financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Recorrência excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recorrência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da recorrência", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/recorrencias/{}", id);
        try {
            service.excluir(id);
            log.info("Recorrência financeira excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Recorrência não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir recorrência financeira — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar recorrência", description = "Inativa uma recorrência financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Recorrência inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recorrência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da recorrência", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/recorrencias/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Recorrência financeira inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Recorrência não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar recorrência financeira — ID: {}", id, ex);
            throw ex;
        }
    }
}

