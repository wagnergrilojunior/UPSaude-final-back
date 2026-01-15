package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.MovimentacaoContaRequest;
import com.upsaude.api.response.financeiro.MovimentacaoContaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.MovimentacaoContaService;
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
@RequestMapping("/api/v1/financeiro/movimentacoes")
@Tag(name = "Financeiro - Movimentações", description = "API para gerenciamento de Movimentações de Conta")
@RequiredArgsConstructor
@Slf4j
public class MovimentacaoContaController {

    private final MovimentacaoContaService service;

    @PostMapping
    @Operation(summary = "Criar movimentação", description = "Cria uma nova movimentação de conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimentação criada com sucesso",
                    content = @Content(schema = @Schema(implementation = MovimentacaoContaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MovimentacaoContaResponse> criar(@Valid @RequestBody MovimentacaoContaRequest request) {
        log.debug("REQUEST POST /api/v1/financeiro/movimentacoes - payload: {}", request);
        try {
            MovimentacaoContaResponse response = service.criar(request);
            log.info("Movimentação criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar movimentação — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar movimentação — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar movimentações", description = "Retorna uma lista paginada de movimentações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MovimentacaoContaResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /api/v1/financeiro/movimentacoes - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar movimentações — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter movimentação por ID", description = "Retorna uma movimentação específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimentação encontrada",
                    content = @Content(schema = @Schema(implementation = MovimentacaoContaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Movimentação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MovimentacaoContaResponse> obterPorId(
            @Parameter(description = "ID da movimentação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /api/v1/financeiro/movimentacoes/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Movimentação não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter movimentação — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar movimentação", description = "Atualiza uma movimentação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimentação atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = MovimentacaoContaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Movimentação não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MovimentacaoContaResponse> atualizar(
            @Parameter(description = "ID da movimentação", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody MovimentacaoContaRequest request) {
        log.debug("REQUEST PUT /api/v1/financeiro/movimentacoes/{} - payload: {}", id, request);
        try {
            MovimentacaoContaResponse response = service.atualizar(id, request);
            log.info("Movimentação atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar movimentação — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar movimentação — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir movimentação", description = "Exclui uma movimentação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movimentação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Movimentação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da movimentação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /api/v1/financeiro/movimentacoes/{}", id);
        try {
            service.excluir(id);
            log.info("Movimentação excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Movimentação não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir movimentação — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar movimentação", description = "Inativa uma movimentação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movimentação inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Movimentação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da movimentação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /api/v1/financeiro/movimentacoes/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Movimentação inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Movimentação não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar movimentação — ID: {}", id, ex);
            throw ex;
        }
    }
}

