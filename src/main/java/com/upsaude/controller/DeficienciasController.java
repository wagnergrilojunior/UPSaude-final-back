package com.upsaude.controller;

import com.upsaude.api.request.DeficienciasRequest;
import com.upsaude.api.response.DeficienciasResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.DeficienciasService;
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

@Slf4j
@RestController
@RequestMapping("/v1/deficiencias")
@Tag(name = "Deficiências", description = "API para gerenciamento de Deficiências")
@RequiredArgsConstructor
public class DeficienciasController {

    private final DeficienciasService deficienciasService;

    @PostMapping
    @Operation(summary = "Criar nova deficiência", description = "Cria uma nova deficiência no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Deficiência criada com sucesso",
                    content = @Content(schema = @Schema(implementation = DeficienciasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DeficienciasResponse> criar(@Valid @RequestBody DeficienciasRequest request) {
        log.debug("REQUEST POST /v1/deficiencias - payload: {}", request);
        try {
            DeficienciasResponse response = deficienciasService.criar(request);
            log.info("Deficiência criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar deficiência — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar deficiência — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar deficiências", description = "Retorna uma lista paginada de deficiências")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de deficiências retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DeficienciasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/deficiencias - pageable: {}", pageable);
        try {
            Page<DeficienciasResponse> response = deficienciasService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar deficiências — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter deficiência por ID", description = "Retorna uma deficiência específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deficiência encontrada",
                    content = @Content(schema = @Schema(implementation = DeficienciasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Deficiência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DeficienciasResponse> obterPorId(
            @Parameter(description = "ID da deficiência", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/deficiencias/{}", id);
        try {
            DeficienciasResponse response = deficienciasService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Deficiência não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter deficiência por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar deficiência", description = "Atualiza uma deficiência existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deficiência atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = DeficienciasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Deficiência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DeficienciasResponse> atualizar(
            @Parameter(description = "ID da deficiência", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DeficienciasRequest request) {
        log.debug("REQUEST PUT /v1/deficiencias/{} - payload: {}", id, request);
        try {
            DeficienciasResponse response = deficienciasService.atualizar(id, request);
            log.info("Deficiência atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar deficiência — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar deficiência — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir deficiência", description = "Exclui (desativa) uma deficiência do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deficiência excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Deficiência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da deficiência", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/deficiencias/{}", id);
        try {
            deficienciasService.excluir(id);
            log.info("Deficiência excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir deficiência — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir deficiência — ID: {}", id, ex);
            throw ex;
        }
    }
}
