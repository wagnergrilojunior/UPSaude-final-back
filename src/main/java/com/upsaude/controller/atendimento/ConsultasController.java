package com.upsaude.controller.atendimento;

import com.upsaude.entity.atendimento.Consultas;

import com.upsaude.api.request.atendimento.ConsultasRequest;
import com.upsaude.api.response.atendimento.ConsultasResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.atendimento.ConsultasService;
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
@RequestMapping("/v1/consultas")
@Tag(name = "Consultas", description = "API para gerenciamento de Consultas")
@RequiredArgsConstructor
@Slf4j
public class ConsultasController {

    private final ConsultasService consultasService;

    @PostMapping
    @Operation(summary = "Criar nova consulta", description = "Cria uma nova consulta no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consulta criada com sucesso",
                    content = @Content(schema = @Schema(implementation = ConsultasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConsultasResponse> criar(@Valid @RequestBody ConsultasRequest request) {
        log.debug("REQUEST POST /v1/consultas - payload: {}", request);
        try {
            ConsultasResponse response = consultasService.criar(request);
            log.info("Consulta criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar consulta — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar consulta — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar consultas", description = "Retorna uma lista paginada de consultas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consultas retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ConsultasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/consultas - pageable: {}", pageable);
        try {
            Page<ConsultasResponse> response = consultasService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar consultas — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter consulta por ID", description = "Retorna uma consulta específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta encontrada",
                    content = @Content(schema = @Schema(implementation = ConsultasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConsultasResponse> obterPorId(
            @Parameter(description = "ID da consulta", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/consultas/{}", id);
        try {
            ConsultasResponse response = consultasService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Consulta não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter consulta por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar consulta", description = "Atualiza uma consulta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = ConsultasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConsultasResponse> atualizar(
            @Parameter(description = "ID da consulta", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ConsultasRequest request) {
        log.debug("REQUEST PUT /v1/consultas/{} - payload: {}", id, request);
        try {
            ConsultasResponse response = consultasService.atualizar(id, request);
            log.info("Consulta atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar consulta — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar consulta — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir consulta", description = "Exclui (desativa) uma consulta do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consulta excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da consulta", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/consultas/{}", id);
        try {
            consultasService.excluir(id);
            log.info("Consulta excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Consulta não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir consulta — ID: {}", id, ex);
            throw ex;
        }
    }
}
