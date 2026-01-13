package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.RegraClassificacaoContabilRequest;
import com.upsaude.api.response.financeiro.RegraClassificacaoContabilResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.RegraClassificacaoContabilService;
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
@RequestMapping("/v1/financeiro/regras-classificacao")
@Tag(name = "Financeiro - Regras de Classificação", description = "API para gerenciamento de Regras de Classificação Contábil")
@RequiredArgsConstructor
@Slf4j
public class RegraClassificacaoContabilController {

    private final RegraClassificacaoContabilService service;

    @PostMapping
    @Operation(summary = "Criar regra", description = "Cria uma nova regra de classificação contábil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Regra criada com sucesso",
                    content = @Content(schema = @Schema(implementation = RegraClassificacaoContabilResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<RegraClassificacaoContabilResponse> criar(@Valid @RequestBody RegraClassificacaoContabilRequest request) {
        log.debug("REQUEST POST /v1/financeiro/regras-classificacao - payload: {}", request);
        try {
            RegraClassificacaoContabilResponse response = service.criar(request);
            log.info("Regra de classificação criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar regra de classificação — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar regra de classificação — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar regras", description = "Retorna uma lista paginada de regras de classificação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<RegraClassificacaoContabilResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/regras-classificacao - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar regras de classificação — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter regra por ID", description = "Retorna uma regra de classificação específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Regra encontrada",
                    content = @Content(schema = @Schema(implementation = RegraClassificacaoContabilResponse.class))),
            @ApiResponse(responseCode = "404", description = "Regra não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<RegraClassificacaoContabilResponse> obterPorId(
            @Parameter(description = "ID da regra", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/regras-classificacao/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Regra não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter regra — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar regra", description = "Atualiza uma regra de classificação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Regra atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = RegraClassificacaoContabilResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Regra não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<RegraClassificacaoContabilResponse> atualizar(
            @Parameter(description = "ID da regra", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody RegraClassificacaoContabilRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/regras-classificacao/{} - payload: {}", id, request);
        try {
            RegraClassificacaoContabilResponse response = service.atualizar(id, request);
            log.info("Regra de classificação atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar regra — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar regra — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir regra", description = "Exclui uma regra de classificação contábil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Regra excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Regra não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da regra", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/regras-classificacao/{}", id);
        try {
            service.excluir(id);
            log.info("Regra excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Regra não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir regra — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar regra", description = "Inativa uma regra de classificação contábil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Regra inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Regra não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da regra", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/regras-classificacao/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Regra inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Regra não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar regra — ID: {}", id, ex);
            throw ex;
        }
    }
}

