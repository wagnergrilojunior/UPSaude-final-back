package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.TransferenciaEntreContasRequest;
import com.upsaude.api.response.financeiro.TransferenciaEntreContasResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.TransferenciaEntreContasService;
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
@RequestMapping("/v1/financeiro/transferencias")
@Tag(name = "Financeiro - Transferências", description = "API para gerenciamento de Transferências entre Contas")
@RequiredArgsConstructor
@Slf4j
public class TransferenciaEntreContasController {

    private final TransferenciaEntreContasService service;

    @PostMapping
    @Operation(summary = "Criar transferência", description = "Cria uma nova transferência entre contas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transferência criada com sucesso",
                    content = @Content(schema = @Schema(implementation = TransferenciaEntreContasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TransferenciaEntreContasResponse> criar(@Valid @RequestBody TransferenciaEntreContasRequest request) {
        log.debug("REQUEST POST /v1/financeiro/transferencias - payload: {}", request);
        try {
            TransferenciaEntreContasResponse response = service.criar(request);
            log.info("Transferência criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar transferência — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar transferência — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar transferências", description = "Retorna uma lista paginada de transferências")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<TransferenciaEntreContasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/transferencias - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar transferências — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter transferência por ID", description = "Retorna uma transferência específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferência encontrada",
                    content = @Content(schema = @Schema(implementation = TransferenciaEntreContasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Transferência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TransferenciaEntreContasResponse> obterPorId(
            @Parameter(description = "ID da transferência", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/transferencias/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Transferência não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter transferência — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar transferência", description = "Atualiza uma transferência existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferência atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = TransferenciaEntreContasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Transferência não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TransferenciaEntreContasResponse> atualizar(
            @Parameter(description = "ID da transferência", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody TransferenciaEntreContasRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/transferencias/{} - payload: {}", id, request);
        try {
            TransferenciaEntreContasResponse response = service.atualizar(id, request);
            log.info("Transferência atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar transferência — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar transferência — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir transferência", description = "Exclui uma transferência")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transferência excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transferência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da transferência", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/transferencias/{}", id);
        try {
            service.excluir(id);
            log.info("Transferência excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Transferência não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir transferência — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar transferência", description = "Inativa uma transferência")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transferência inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transferência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da transferência", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/transferencias/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Transferência inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Transferência não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar transferência — ID: {}", id, ex);
            throw ex;
        }
    }
}

