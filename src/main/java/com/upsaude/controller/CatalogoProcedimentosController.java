package com.upsaude.controller;

import com.upsaude.api.request.CatalogoProcedimentosRequest;
import com.upsaude.api.response.CatalogoProcedimentosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/catalogo-procedimentos")
@Tag(name = "Catálogo de Procedimentos", description = "API para gerenciamento de Catálogo de Procedimentos")
@RequiredArgsConstructor
@Slf4j
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
        log.debug("REQUEST POST /v1/catalogo-procedimentos - payload: {}", request);
        try {
            CatalogoProcedimentosResponse response = catalogoProcedimentosService.criar(request);
            log.info("Procedimento do catálogo criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar procedimento no catálogo — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar procedimento no catálogo — payload: {}", request, ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/catalogo-procedimentos - pageable: {}", pageable);
        try {
            Page<CatalogoProcedimentosResponse> response = catalogoProcedimentosService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar procedimentos do catálogo — pageable: {}", pageable, ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/catalogo-procedimentos/{}", id);
        try {
            CatalogoProcedimentosResponse response = catalogoProcedimentosService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Falha ao obter procedimento do catálogo por ID — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter procedimento do catálogo por ID — ID: {}", id, ex);
            throw ex;
        }
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
        log.debug("REQUEST PUT /v1/catalogo-procedimentos/{} - payload: {}", id, request);
        try {
            CatalogoProcedimentosResponse response = catalogoProcedimentosService.atualizar(id, request);
            log.info("Procedimento do catálogo atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar procedimento do catálogo — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar procedimento do catálogo — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
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
        log.debug("REQUEST DELETE /v1/catalogo-procedimentos/{}", id);
        try {
            catalogoProcedimentosService.excluir(id);
            log.info("Procedimento do catálogo excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir procedimento do catálogo — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir procedimento do catálogo — ID: {}", id, ex);
            throw ex;
        }
    }
}
