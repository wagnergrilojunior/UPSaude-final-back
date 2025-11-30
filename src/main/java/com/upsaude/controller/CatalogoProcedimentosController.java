package com.upsaude.controller;

import com.upsaude.api.request.CatalogoProcedimentosRequest;
import com.upsaude.api.response.CatalogoProcedimentosResponse;
import com.upsaude.service.CatalogoProcedimentosService;
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
 * Controlador REST para operações relacionadas a Catálogo de Procedimentos.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/catalogo-procedimentos")
@Tag(name = "Catálogo de Procedimentos", description = "API para gerenciamento de Catálogo de Procedimentos")
@RequiredArgsConstructor
public class CatalogoProcedimentosController {

    private final CatalogoProcedimentosService catalogoProcedimentosService;

    @PostMapping
    @Operation(summary = "Criar novo procedimento no catálogo", description = "Cria um novo procedimento no catálogo de procedimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Procedimento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = CatalogoProcedimentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CatalogoProcedimentosResponse> criar(@Valid @RequestBody CatalogoProcedimentosRequest request) {
        CatalogoProcedimentosResponse response = catalogoProcedimentosService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar procedimentos do catálogo", description = "Retorna uma lista paginada de procedimentos do catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de procedimentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CatalogoProcedimentosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<CatalogoProcedimentosResponse> response = catalogoProcedimentosService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter procedimento por ID", description = "Retorna um procedimento específico do catálogo pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Procedimento encontrado",
                    content = @Content(schema = @Schema(implementation = CatalogoProcedimentosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Procedimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CatalogoProcedimentosResponse> obterPorId(
            @Parameter(description = "ID do procedimento", required = true)
            @PathVariable UUID id) {
        CatalogoProcedimentosResponse response = catalogoProcedimentosService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar procedimento", description = "Atualiza um procedimento existente no catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Procedimento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = CatalogoProcedimentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Procedimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CatalogoProcedimentosResponse> atualizar(
            @Parameter(description = "ID do procedimento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody CatalogoProcedimentosRequest request) {
        CatalogoProcedimentosResponse response = catalogoProcedimentosService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir procedimento", description = "Exclui (desativa) um procedimento do catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Procedimento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Procedimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do procedimento", required = true)
            @PathVariable UUID id) {
        catalogoProcedimentosService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

