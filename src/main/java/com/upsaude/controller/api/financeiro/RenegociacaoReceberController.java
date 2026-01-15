package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.RenegociacaoReceberRequest;
import com.upsaude.api.response.financeiro.RenegociacaoReceberResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.RenegociacaoReceberService;
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
@RequestMapping("/api/v1/financeiro/renegociacoes-receber")
@Tag(name = "Financeiro - Renegociações (Receber)", description = "API para gerenciamento de Renegociações de Títulos a Receber")
@RequiredArgsConstructor
@Slf4j
public class RenegociacaoReceberController {

    private final RenegociacaoReceberService service;

    @PostMapping
    @Operation(summary = "Criar renegociação", description = "Cria uma nova renegociação de títulos a receber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Renegociação criada com sucesso",
                    content = @Content(schema = @Schema(implementation = RenegociacaoReceberResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<RenegociacaoReceberResponse> criar(@Valid @RequestBody RenegociacaoReceberRequest request) {
        log.debug("REQUEST POST /api/v1/financeiro/renegociacoes-receber - payload: {}", request);
        try {
            RenegociacaoReceberResponse response = service.criar(request);
            log.info("Renegociação criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar renegociação — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar renegociação — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar renegociações", description = "Retorna uma lista paginada de renegociações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<RenegociacaoReceberResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /api/v1/financeiro/renegociacoes-receber - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar renegociações — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter renegociação por ID", description = "Retorna uma renegociação específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Renegociação encontrada",
                    content = @Content(schema = @Schema(implementation = RenegociacaoReceberResponse.class))),
            @ApiResponse(responseCode = "404", description = "Renegociação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<RenegociacaoReceberResponse> obterPorId(
            @Parameter(description = "ID da renegociação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /api/v1/financeiro/renegociacoes-receber/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Renegociação não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter renegociação — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar renegociação", description = "Atualiza uma renegociação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Renegociação atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = RenegociacaoReceberResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Renegociação não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<RenegociacaoReceberResponse> atualizar(
            @Parameter(description = "ID da renegociação", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody RenegociacaoReceberRequest request) {
        log.debug("REQUEST PUT /api/v1/financeiro/renegociacoes-receber/{} - payload: {}", id, request);
        try {
            RenegociacaoReceberResponse response = service.atualizar(id, request);
            log.info("Renegociação atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar renegociação — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar renegociação — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir renegociação", description = "Exclui uma renegociação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Renegociação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Renegociação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da renegociação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /api/v1/financeiro/renegociacoes-receber/{}", id);
        try {
            service.excluir(id);
            log.info("Renegociação excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Renegociação não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir renegociação — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar renegociação", description = "Inativa uma renegociação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Renegociação inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Renegociação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da renegociação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /api/v1/financeiro/renegociacoes-receber/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Renegociação inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Renegociação não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar renegociação — ID: {}", id, ex);
            throw ex;
        }
    }
}

