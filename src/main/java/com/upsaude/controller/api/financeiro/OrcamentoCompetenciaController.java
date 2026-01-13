package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.OrcamentoCompetenciaRequest;
import com.upsaude.api.response.financeiro.OrcamentoCompetenciaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.OrcamentoCompetenciaService;
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
@RequestMapping("/v1/financeiro/orcamentos-competencia")
@Tag(name = "Financeiro - Orçamentos por Competência", description = "API para gerenciamento de Orçamento por competência (tenant/município)")
@RequiredArgsConstructor
@Slf4j
public class OrcamentoCompetenciaController {

    private final OrcamentoCompetenciaService service;

    @PostMapping
    @Operation(summary = "Criar orçamento de competência", description = "Cria um novo orçamento por competência (por tenant)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orçamento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = OrcamentoCompetenciaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<OrcamentoCompetenciaResponse> criar(@Valid @RequestBody OrcamentoCompetenciaRequest request) {
        log.debug("REQUEST POST /v1/financeiro/orcamentos-competencia - payload: {}", request);
        try {
            OrcamentoCompetenciaResponse response = service.criar(request);
            log.info("Orçamento de competência criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar orçamento de competência — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar orçamento de competência — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar orçamentos de competência", description = "Retorna uma lista paginada de orçamentos de competência")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<OrcamentoCompetenciaResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/orcamentos-competencia - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar orçamentos de competência — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter orçamento por ID", description = "Retorna um orçamento de competência específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orçamento encontrado",
                    content = @Content(schema = @Schema(implementation = OrcamentoCompetenciaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<OrcamentoCompetenciaResponse> obterPorId(
            @Parameter(description = "ID do orçamento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/orcamentos-competencia/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Orçamento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter orçamento — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar orçamento", description = "Atualiza um orçamento de competência existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orçamento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = OrcamentoCompetenciaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<OrcamentoCompetenciaResponse> atualizar(
            @Parameter(description = "ID do orçamento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody OrcamentoCompetenciaRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/orcamentos-competencia/{} - payload: {}", id, request);
        try {
            OrcamentoCompetenciaResponse response = service.atualizar(id, request);
            log.info("Orçamento atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar orçamento — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar orçamento — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir orçamento", description = "Exclui um orçamento de competência")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Orçamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do orçamento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/orcamentos-competencia/{}", id);
        try {
            service.excluir(id);
            log.info("Orçamento excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Orçamento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir orçamento — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar orçamento", description = "Inativa um orçamento de competência")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Orçamento inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Orçamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do orçamento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/orcamentos-competencia/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Orçamento inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Orçamento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar orçamento — ID: {}", id, ex);
            throw ex;
        }
    }
}

