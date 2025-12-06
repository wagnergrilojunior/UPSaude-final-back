package com.upsaude.controller;

import com.upsaude.api.request.CatalogoExamesRequest;
import com.upsaude.api.response.CatalogoExamesResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.CatalogoExamesService;
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

/**
 * Controlador REST para operações relacionadas a Catálogo de Exames.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/catalogo-exames")
@Tag(name = "Catálogo de Exames", description = "API para gerenciamento de Catálogo de Exames")
@RequiredArgsConstructor
@Slf4j
public class CatalogoExamesController {

    private final CatalogoExamesService catalogoExamesService;

    @PostMapping
    @Operation(summary = "Criar novo exame no catálogo", description = "Cria um novo exame no catálogo de exames")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exame criado com sucesso",
                    content = @Content(schema = @Schema(implementation = CatalogoExamesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CatalogoExamesResponse> criar(@Valid @RequestBody CatalogoExamesRequest request) {
        log.debug("REQUEST POST /v1/catalogo-exames - payload: {}", request);
        try {
            CatalogoExamesResponse response = catalogoExamesService.criar(request);
            log.info("Exame do catálogo criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar exame no catálogo — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar exame no catálogo — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar exames do catálogo", description = "Retorna uma lista paginada de exames do catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de exames retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CatalogoExamesResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/catalogo-exames - pageable: {}", pageable);
        try {
            Page<CatalogoExamesResponse> response = catalogoExamesService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar exames do catálogo — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter exame por ID", description = "Retorna um exame específico do catálogo pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame encontrado",
                    content = @Content(schema = @Schema(implementation = CatalogoExamesResponse.class))),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CatalogoExamesResponse> obterPorId(
            @Parameter(description = "ID do exame", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/catalogo-exames/{}", id);
        try {
            CatalogoExamesResponse response = catalogoExamesService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Falha ao obter exame do catálogo por ID — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter exame do catálogo por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar exame", description = "Atualiza um exame existente no catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = CatalogoExamesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CatalogoExamesResponse> atualizar(
            @Parameter(description = "ID do exame", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody CatalogoExamesRequest request) {
        log.debug("REQUEST PUT /v1/catalogo-exames/{} - payload: {}", id, request);
        try {
            CatalogoExamesResponse response = catalogoExamesService.atualizar(id, request);
            log.info("Exame do catálogo atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar exame do catálogo — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar exame do catálogo — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir exame", description = "Exclui (desativa) um exame do catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exame excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do exame", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/catalogo-exames/{}", id);
        try {
            catalogoExamesService.excluir(id);
            log.info("Exame do catálogo excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir exame do catálogo — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir exame do catálogo — ID: {}", id, ex);
            throw ex;
        }
    }
}

