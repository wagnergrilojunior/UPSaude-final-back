package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.CreditoOrcamentarioRequest;
import com.upsaude.api.response.financeiro.CreditoOrcamentarioResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.CreditoOrcamentarioService;
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
@RequestMapping("/v1/financeiro/creditos-orcamentarios")
@Tag(name = "Financeiro - Créditos Orçamentários", description = "API para gerenciamento de Créditos Orçamentários")
@RequiredArgsConstructor
@Slf4j
public class CreditoOrcamentarioController {

    private final CreditoOrcamentarioService service;

    @PostMapping
    @Operation(summary = "Criar crédito orçamentário", description = "Cria um novo crédito orçamentário para uma competência")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Crédito criado com sucesso",
                    content = @Content(schema = @Schema(implementation = CreditoOrcamentarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CreditoOrcamentarioResponse> criar(@Valid @RequestBody CreditoOrcamentarioRequest request) {
        log.debug("REQUEST POST /v1/financeiro/creditos-orcamentarios - payload: {}", request);
        try {
            CreditoOrcamentarioResponse response = service.criar(request);
            log.info("Crédito orçamentário criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar crédito orçamentário — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar crédito orçamentário — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar créditos orçamentários", description = "Retorna uma lista paginada de créditos orçamentários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CreditoOrcamentarioResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/creditos-orcamentarios - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar créditos orçamentários — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter crédito por ID", description = "Retorna um crédito orçamentário específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crédito encontrado",
                    content = @Content(schema = @Schema(implementation = CreditoOrcamentarioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Crédito não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CreditoOrcamentarioResponse> obterPorId(
            @Parameter(description = "ID do crédito", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/creditos-orcamentarios/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Crédito não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter crédito orçamentário — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar crédito", description = "Atualiza um crédito orçamentário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crédito atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = CreditoOrcamentarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Crédito não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CreditoOrcamentarioResponse> atualizar(
            @Parameter(description = "ID do crédito", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody CreditoOrcamentarioRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/creditos-orcamentarios/{} - payload: {}", id, request);
        try {
            CreditoOrcamentarioResponse response = service.atualizar(id, request);
            log.info("Crédito orçamentário atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar crédito orçamentário — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar crédito orçamentário — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir crédito", description = "Exclui um crédito orçamentário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Crédito excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Crédito não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do crédito", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/creditos-orcamentarios/{}", id);
        try {
            service.excluir(id);
            log.info("Crédito orçamentário excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Crédito não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir crédito orçamentário — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar crédito", description = "Inativa um crédito orçamentário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Crédito inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Crédito não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do crédito", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/creditos-orcamentarios/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Crédito orçamentário inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Crédito não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar crédito orçamentário — ID: {}", id, ex);
            throw ex;
        }
    }
}

