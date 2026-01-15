package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.ContaContabilRequest;
import com.upsaude.api.response.financeiro.ContaContabilResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.ContaContabilService;
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
@RequestMapping("/api/v1/financeiro/contas-contabeis")
@Tag(name = "Financeiro - Contas Contábeis", description = "API para gerenciamento de Conta Contábil")
@RequiredArgsConstructor
@Slf4j
public class ContaContabilController {

    private final ContaContabilService service;

    @PostMapping
    @Operation(summary = "Criar conta contábil", description = "Cria uma nova conta contábil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta contábil criada com sucesso",
                    content = @Content(schema = @Schema(implementation = ContaContabilResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ContaContabilResponse> criar(@Valid @RequestBody ContaContabilRequest request) {
        log.debug("REQUEST POST /api/v1/financeiro/contas-contabeis - payload: {}", request);
        try {
            ContaContabilResponse response = service.criar(request);
            log.info("Conta contábil criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao criar conta contábil — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar conta contábil — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar contas contábeis", description = "Retorna uma lista paginada de contas contábeis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ContaContabilResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /api/v1/financeiro/contas-contabeis - pageable: {}", pageable);
        try {
            Page<ContaContabilResponse> response = service.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar contas contábeis — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter conta contábil por ID", description = "Retorna uma conta contábil específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta contábil encontrada",
                    content = @Content(schema = @Schema(implementation = ContaContabilResponse.class))),
            @ApiResponse(responseCode = "404", description = "Conta contábil não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ContaContabilResponse> obterPorId(
            @Parameter(description = "ID da conta contábil", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /api/v1/financeiro/contas-contabeis/{}", id);
        try {
            ContaContabilResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Conta contábil não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter conta contábil — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar conta contábil", description = "Atualiza uma conta contábil existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta contábil atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = ContaContabilResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Conta contábil não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ContaContabilResponse> atualizar(
            @Parameter(description = "ID da conta contábil", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ContaContabilRequest request) {
        log.debug("REQUEST PUT /api/v1/financeiro/contas-contabeis/{} - payload: {}", id, request);
        try {
            ContaContabilResponse response = service.atualizar(id, request);
            log.info("Conta contábil atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar conta contábil — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar conta contábil — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir conta contábil", description = "Exclui uma conta contábil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conta contábil excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta contábil não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da conta contábil", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /api/v1/financeiro/contas-contabeis/{}", id);
        try {
            service.excluir(id);
            log.info("Conta contábil excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Conta contábil não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir conta contábil — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar conta contábil", description = "Inativa uma conta contábil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conta contábil inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta contábil não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da conta contábil", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /api/v1/financeiro/contas-contabeis/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Conta contábil inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Conta contábil não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar conta contábil — ID: {}", id, ex);
            throw ex;
        }
    }
}

