package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.ConciliacaoBancariaRequest;
import com.upsaude.api.response.financeiro.ConciliacaoBancariaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.ConciliacaoBancariaService;
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
@RequestMapping("/v1/financeiro/conciliacoes")
@Tag(name = "Financeiro - Conciliações", description = "API para gerenciamento de Conciliação Bancária")
@RequiredArgsConstructor
@Slf4j
public class ConciliacaoBancariaController {

    private final ConciliacaoBancariaService service;

    @PostMapping
    @Operation(summary = "Criar conciliação", description = "Cria uma nova conciliação bancária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conciliação criada com sucesso",
                    content = @Content(schema = @Schema(implementation = ConciliacaoBancariaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConciliacaoBancariaResponse> criar(@Valid @RequestBody ConciliacaoBancariaRequest request) {
        log.debug("REQUEST POST /v1/financeiro/conciliacoes - payload: {}", request);
        try {
            ConciliacaoBancariaResponse response = service.criar(request);
            log.info("Conciliação criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar conciliação — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar conciliação — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar conciliações", description = "Retorna uma lista paginada de conciliações bancárias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ConciliacaoBancariaResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/conciliacoes - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar conciliações — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter conciliação por ID", description = "Retorna uma conciliação bancária específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conciliação encontrada",
                    content = @Content(schema = @Schema(implementation = ConciliacaoBancariaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Conciliação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConciliacaoBancariaResponse> obterPorId(
            @Parameter(description = "ID da conciliação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/conciliacoes/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Conciliação não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter conciliação — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar conciliação", description = "Atualiza uma conciliação bancária existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conciliação atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = ConciliacaoBancariaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Conciliação não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConciliacaoBancariaResponse> atualizar(
            @Parameter(description = "ID da conciliação", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ConciliacaoBancariaRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/conciliacoes/{} - payload: {}", id, request);
        try {
            ConciliacaoBancariaResponse response = service.atualizar(id, request);
            log.info("Conciliação atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar conciliação — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar conciliação — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir conciliação", description = "Exclui uma conciliação bancária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conciliação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conciliação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da conciliação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/conciliacoes/{}", id);
        try {
            service.excluir(id);
            log.info("Conciliação excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Conciliação não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir conciliação — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar conciliação", description = "Inativa uma conciliação bancária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conciliação inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conciliação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da conciliação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/conciliacoes/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Conciliação inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Conciliação não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar conciliação — ID: {}", id, ex);
            throw ex;
        }
    }
}

