package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.ContaFinanceiraRequest;
import com.upsaude.api.response.financeiro.ContaFinanceiraResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.ContaFinanceiraService;
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
@RequestMapping("/v1/financeiro/contas-financeiras")
@Tag(name = "Financeiro - Contas Financeiras", description = "API para gerenciamento de Conta Financeira")
@RequiredArgsConstructor
@Slf4j
public class ContaFinanceiraController {

    private final ContaFinanceiraService service;

    @PostMapping
    @Operation(summary = "Criar conta financeira", description = "Cria uma nova conta financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta financeira criada com sucesso",
                    content = @Content(schema = @Schema(implementation = ContaFinanceiraResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ContaFinanceiraResponse> criar(@Valid @RequestBody ContaFinanceiraRequest request) {
        log.debug("REQUEST POST /v1/financeiro/contas-financeiras - payload: {}", request);
        try {
            ContaFinanceiraResponse response = service.criar(request);
            log.info("Conta financeira criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao criar conta financeira — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar conta financeira — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar contas financeiras", description = "Retorna uma lista paginada de contas financeiras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ContaFinanceiraResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/contas-financeiras - pageable: {}", pageable);
        try {
            Page<ContaFinanceiraResponse> response = service.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar contas financeiras — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter conta financeira por ID", description = "Retorna uma conta financeira específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta financeira encontrada",
                    content = @Content(schema = @Schema(implementation = ContaFinanceiraResponse.class))),
            @ApiResponse(responseCode = "404", description = "Conta financeira não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ContaFinanceiraResponse> obterPorId(
            @Parameter(description = "ID da conta financeira", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/contas-financeiras/{}", id);
        try {
            ContaFinanceiraResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Conta financeira não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter conta financeira — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar conta financeira", description = "Atualiza uma conta financeira existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta financeira atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = ContaFinanceiraResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Conta financeira não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ContaFinanceiraResponse> atualizar(
            @Parameter(description = "ID da conta financeira", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ContaFinanceiraRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/contas-financeiras/{} - payload: {}", id, request);
        try {
            ContaFinanceiraResponse response = service.atualizar(id, request);
            log.info("Conta financeira atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar conta financeira — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar conta financeira — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir conta financeira", description = "Exclui uma conta financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conta financeira excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta financeira não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da conta financeira", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/contas-financeiras/{}", id);
        try {
            service.excluir(id);
            log.info("Conta financeira excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Conta financeira não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir conta financeira — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar conta financeira", description = "Inativa uma conta financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conta financeira inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta financeira não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da conta financeira", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/contas-financeiras/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Conta financeira inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Conta financeira não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar conta financeira — ID: {}", id, ex);
            throw ex;
        }
    }
}

