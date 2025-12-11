package com.upsaude.controller;

import com.upsaude.api.request.TenantRequest;
import com.upsaude.api.response.TenantResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.TenantService;
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
@RequestMapping("/v1/tenants")
@Tag(name = "Tenants", description = "API para gerenciamento de Tenants")
@RequiredArgsConstructor
@Slf4j
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    @Operation(summary = "Criar novo tenant", description = "Cria um novo tenant no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tenant criado com sucesso",
                    content = @Content(schema = @Schema(implementation = TenantResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TenantResponse> criar(@Valid @RequestBody TenantRequest request) {
        log.debug("REQUEST POST /v1/tenants - payload: {}", request);
        try {
            TenantResponse response = tenantService.criar(request);
            log.info("Tenant criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar tenant — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar tenant — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar tenants", description = "Retorna uma lista paginada de tenants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tenants retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<TenantResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/tenants - pageable: {}", pageable);
        try {
            Page<TenantResponse> response = tenantService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar tenants — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter tenant por ID", description = "Retorna um tenant específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenant encontrado",
                    content = @Content(schema = @Schema(implementation = TenantResponse.class))),
            @ApiResponse(responseCode = "404", description = "Tenant não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TenantResponse> obterPorId(
            @Parameter(description = "ID do tenant", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/tenants/{}", id);
        try {
            TenantResponse response = tenantService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Tenant não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter tenant por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tenant", description = "Atualiza um tenant existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenant atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = TenantResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Tenant não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TenantResponse> atualizar(
            @Parameter(description = "ID do tenant", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody TenantRequest request) {
        log.debug("REQUEST PUT /v1/tenants/{} - payload: {}", id, request);
        try {
            TenantResponse response = tenantService.atualizar(id, request);
            log.info("Tenant atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar tenant — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar tenant — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tenant", description = "Exclui (desativa) um tenant do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tenant excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tenant não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do tenant", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/tenants/{}", id);
        try {
            tenantService.excluir(id);
            log.info("Tenant excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Tenant não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir tenant — ID: {}", id, ex);
            throw ex;
        }
    }
}
