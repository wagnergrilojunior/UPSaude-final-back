package com.upsaude.controller;

import com.upsaude.api.request.AlergiasRequest;
import com.upsaude.api.response.AlergiasResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.exception.ConflictException;
import com.upsaude.service.AlergiasService;
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
@RequestMapping("/v1/alergias")
@Tag(name = "Alergias", description = "API para gerenciamento de Alergias")
@RequiredArgsConstructor
@Slf4j
public class AlergiasController {

    private final AlergiasService alergiasService;

    @PostMapping
    @Operation(summary = "Criar nova alergia", description = "Cria uma nova alergia no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alergia criada com sucesso",
                    content = @Content(schema = @Schema(implementation = AlergiasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiasResponse> criar(@Valid @RequestBody AlergiasRequest request) {
        log.debug("REQUEST POST /v1/alergias - payload: {}", request);
        try {
            AlergiasResponse response = alergiasService.criar(request);
            log.info("Alergia criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar alergia — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar alergia — Path: /v1/alergias, Method: POST, payload: {}, Exception: {}",
                request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar alergias", description = "Retorna uma lista paginada de alergias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alergias retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AlergiasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/alergias - pageable: page={}, size={}, sort={}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        try {
            Page<AlergiasResponse> response = alergiasService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar alergias — Path: /v1/alergias, Method: GET, pageable: {}, Exception: {}",
                pageable, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter alergia por ID", description = "Retorna uma alergia específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alergia encontrada",
                    content = @Content(schema = @Schema(implementation = AlergiasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Alergia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiasResponse> obterPorId(
            @Parameter(description = "ID da alergia", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/alergias/{} ", id);
        try {
            AlergiasResponse response = alergiasService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Alergia não encontrada — ID: {}", id);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao buscar alergia — Path: /v1/alergias/{}, Method: GET, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar alergia", description = "Atualiza uma alergia existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alergia atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = AlergiasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Alergia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiasResponse> atualizar(
            @Parameter(description = "ID da alergia", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody AlergiasRequest request) {
        log.debug("REQUEST PUT /v1/alergias/{} - payload: {}", id, request);
        try {
            AlergiasResponse response = alergiasService.atualizar(id, request);
            log.info("Alergia atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar alergia — id: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar alergia — Path: /v1/alergias/{}, Method: PUT, ID: {}, payload: {}, Exception: {}",
                id, id, request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir alergia", description = "Exclui (desativa) uma alergia do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alergia excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alergia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da alergia", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/alergias/{} ", id);
        try {
            alergiasService.excluir(id);
            log.info("Alergia desativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao excluir alergia — id: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir alergia — Path: /v1/alergias/{}, Method: DELETE, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }
}
