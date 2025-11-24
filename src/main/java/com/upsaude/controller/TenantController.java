package com.upsaude.controller;

import com.upsaude.api.request.TenantRequest;
import com.upsaude.api.response.TenantResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a Tenants.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/tenants")
@Tag(name = "Tenants", description = "API para gerenciamento de Tenants")
@RequiredArgsConstructor
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
        TenantResponse response = tenantService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
        Page<TenantResponse> response = tenantService.listar(pageable);
        return ResponseEntity.ok(response);
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
        TenantResponse response = tenantService.obterPorId(id);
        return ResponseEntity.ok(response);
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
        TenantResponse response = tenantService.atualizar(id, request);
        return ResponseEntity.ok(response);
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
        tenantService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

