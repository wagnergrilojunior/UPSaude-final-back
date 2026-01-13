package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraRequest;
import com.upsaude.api.response.financeiro.CompetenciaFinanceiraResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.CompetenciaFinanceiraService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/financeiro/competencias")
@Tag(name = "Financeiro - Competências", description = "API para gerenciamento de Competência Financeira")
@RequiredArgsConstructor
@Slf4j
public class CompetenciaFinanceiraController {

    private final CompetenciaFinanceiraService service;

    @PostMapping
    @Operation(summary = "Criar competência financeira", description = "Cria uma nova competência financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Competência criada com sucesso",
                    content = @Content(schema = @Schema(implementation = CompetenciaFinanceiraResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CompetenciaFinanceiraResponse> criar(@Valid @RequestBody CompetenciaFinanceiraRequest request) {
        log.debug("REQUEST POST /v1/financeiro/competencias - payload: {}", request);
        try {
            CompetenciaFinanceiraResponse response = service.criar(request);
            log.info("Competência financeira criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao criar competência financeira — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar competência financeira — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar competências financeiras", description = "Retorna uma lista paginada de competências financeiras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CompetenciaFinanceiraResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/competencias - pageable: {}", pageable);
        try {
            Page<CompetenciaFinanceiraResponse> response = service.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar competências financeiras — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter competência por ID", description = "Retorna uma competência financeira específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Competência encontrada",
                    content = @Content(schema = @Schema(implementation = CompetenciaFinanceiraResponse.class))),
            @ApiResponse(responseCode = "404", description = "Competência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CompetenciaFinanceiraResponse> obterPorId(
            @Parameter(description = "ID da competência", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/competencias/{}", id);
        try {
            CompetenciaFinanceiraResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Competência não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter competência financeira — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar competência financeira", description = "Atualiza uma competência financeira existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Competência atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = CompetenciaFinanceiraResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Competência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CompetenciaFinanceiraResponse> atualizar(
            @Parameter(description = "ID da competência", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody CompetenciaFinanceiraRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/competencias/{} - payload: {}", id, request);
        try {
            CompetenciaFinanceiraResponse response = service.atualizar(id, request);
            log.info("Competência financeira atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar competência financeira — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar competência financeira — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir competência financeira", description = "Exclui uma competência financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Competência excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Competência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da competência", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/competencias/{}", id);
        try {
            service.excluir(id);
            log.info("Competência financeira excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Competência não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir competência financeira — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar competência financeira", description = "Inativa uma competência financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Competência inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Competência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da competência", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/competencias/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Competência financeira inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Competência não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar competência financeira — ID: {}", id, ex);
            throw ex;
        }
    }
}

