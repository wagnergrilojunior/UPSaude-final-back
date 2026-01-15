package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.PlanoContasRequest;
import com.upsaude.api.response.financeiro.PlanoContasResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.PlanoContasService;
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
@RequestMapping("/api/v1/financeiro/planos-contas")
@Tag(name = "Financeiro - Plano de Contas", description = "API para gerenciamento de Plano de Contas")
@RequiredArgsConstructor
@Slf4j
public class PlanoContasController {

    private final PlanoContasService service;

    @PostMapping
    @Operation(summary = "Criar plano de contas", description = "Cria um novo plano de contas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plano de contas criado com sucesso",
                    content = @Content(schema = @Schema(implementation = PlanoContasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PlanoContasResponse> criar(@Valid @RequestBody PlanoContasRequest request) {
        log.debug("REQUEST POST /api/v1/financeiro/planos-contas - payload: {}", request);
        try {
            PlanoContasResponse response = service.criar(request);
            log.info("Plano de contas criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao criar plano de contas — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar plano de contas — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar planos de contas", description = "Retorna uma lista paginada de planos de contas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<PlanoContasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /api/v1/financeiro/planos-contas - pageable: {}", pageable);
        try {
            Page<PlanoContasResponse> response = service.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar planos de contas — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter plano de contas por ID", description = "Retorna um plano de contas específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano de contas encontrado",
                    content = @Content(schema = @Schema(implementation = PlanoContasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Plano de contas não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PlanoContasResponse> obterPorId(
            @Parameter(description = "ID do plano de contas", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /api/v1/financeiro/planos-contas/{}", id);
        try {
            PlanoContasResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Plano de contas não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter plano de contas — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar plano de contas", description = "Atualiza um plano de contas existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano de contas atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = PlanoContasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Plano de contas não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PlanoContasResponse> atualizar(
            @Parameter(description = "ID do plano de contas", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody PlanoContasRequest request) {
        log.debug("REQUEST PUT /api/v1/financeiro/planos-contas/{} - payload: {}", id, request);
        try {
            PlanoContasResponse response = service.atualizar(id, request);
            log.info("Plano de contas atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar plano de contas — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar plano de contas — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir plano de contas", description = "Exclui um plano de contas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plano de contas excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano de contas não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do plano de contas", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /api/v1/financeiro/planos-contas/{}", id);
        try {
            service.excluir(id);
            log.info("Plano de contas excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Plano de contas não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir plano de contas — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar plano de contas", description = "Inativa um plano de contas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plano de contas inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano de contas não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do plano de contas", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /api/v1/financeiro/planos-contas/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Plano de contas inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Plano de contas não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar plano de contas — ID: {}", id, ex);
            throw ex;
        }
    }
}

