package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraTenantRequest;
import com.upsaude.api.response.financeiro.CompetenciaFinanceiraTenantResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.CompetenciaFinanceiraTenantService;
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
@RequestMapping("/v1/financeiro/competencias-tenant")
@Tag(name = "Financeiro - Competências (Tenant)", description = "API para gerenciamento de Competência Financeira por Tenant (município)")
@RequiredArgsConstructor
@Slf4j
public class CompetenciaFinanceiraTenantController {

    private final CompetenciaFinanceiraTenantService service;

    @PostMapping
    @Operation(summary = "Criar competência-tenant", description = "Cria um registro de competência financeira por tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Competência-tenant criada com sucesso",
                    content = @Content(schema = @Schema(implementation = CompetenciaFinanceiraTenantResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CompetenciaFinanceiraTenantResponse> criar(@Valid @RequestBody CompetenciaFinanceiraTenantRequest request) {
        log.debug("REQUEST POST /v1/financeiro/competencias-tenant - payload: {}", request);
        try {
            CompetenciaFinanceiraTenantResponse response = service.criar(request);
            log.info("Competência-tenant criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao criar competência-tenant — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar competência-tenant — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar competências-tenant", description = "Retorna uma lista paginada de competências-tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CompetenciaFinanceiraTenantResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/competencias-tenant - pageable: {}", pageable);
        try {
            Page<CompetenciaFinanceiraTenantResponse> response = service.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar competências-tenant — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter competência-tenant por ID", description = "Retorna uma competência-tenant específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Competência-tenant encontrada",
                    content = @Content(schema = @Schema(implementation = CompetenciaFinanceiraTenantResponse.class))),
            @ApiResponse(responseCode = "404", description = "Competência-tenant não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CompetenciaFinanceiraTenantResponse> obterPorId(
            @Parameter(description = "ID da competência-tenant", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/competencias-tenant/{}", id);
        try {
            CompetenciaFinanceiraTenantResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Competência-tenant não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter competência-tenant — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar competência-tenant", description = "Atualiza uma competência-tenant existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Competência-tenant atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = CompetenciaFinanceiraTenantResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Competência-tenant não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CompetenciaFinanceiraTenantResponse> atualizar(
            @Parameter(description = "ID da competência-tenant", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody CompetenciaFinanceiraTenantRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/competencias-tenant/{} - payload: {}", id, request);
        try {
            CompetenciaFinanceiraTenantResponse response = service.atualizar(id, request);
            log.info("Competência-tenant atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar competência-tenant — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar competência-tenant — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir competência-tenant", description = "Exclui uma competência-tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Competência-tenant excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Competência-tenant não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da competência-tenant", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/competencias-tenant/{}", id);
        try {
            service.excluir(id);
            log.info("Competência-tenant excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Competência-tenant não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir competência-tenant — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar competência-tenant", description = "Inativa uma competência-tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Competência-tenant inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Competência-tenant não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da competência-tenant", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/competencias-tenant/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Competência-tenant inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Competência-tenant não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar competência-tenant — ID: {}", id, ex);
            throw ex;
        }
    }
}

