package com.upsaude.controller;

import com.upsaude.api.request.ConsultaPuericulturaRequest;
import com.upsaude.api.response.ConsultaPuericulturaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.ConsultaPuericulturaService;
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
@RequestMapping("/v1/consultas-puericultura")
@Tag(name = "Consultas Puericultura", description = "API para gerenciamento de consultas de puericultura")
@RequiredArgsConstructor
@Slf4j
public class ConsultaPuericulturaController {

    private final ConsultaPuericulturaService service;

    @PostMapping
    @Operation(summary = "Criar nova consulta de puericultura", description = "Cria uma nova consulta de puericultura no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Consulta criada com sucesso",
            content = @Content(schema = @Schema(implementation = ConsultaPuericulturaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConsultaPuericulturaResponse> criar(@Valid @RequestBody ConsultaPuericulturaRequest request) {
        log.debug("REQUEST POST /v1/consultas-puericultura - payload: {}", request);
        try {
            ConsultaPuericulturaResponse response = service.criar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar consulta de puericultura — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar consulta de puericultura — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar consultas de puericultura", description = "Retorna uma lista paginada de consultas de puericultura")
    public ResponseEntity<Page<ConsultaPuericulturaResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable,
        @RequestParam(required = false) UUID puericulturaId,
        @RequestParam(required = false) UUID estabelecimentoId) {
        log.debug("REQUEST GET /v1/consultas-puericultura - pageable: {}, puericulturaId: {}, estabelecimentoId: {}",
            pageable, puericulturaId, estabelecimentoId);
        Page<ConsultaPuericulturaResponse> response = service.listar(pageable, puericulturaId, estabelecimentoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter consulta de puericultura por ID", description = "Retorna uma consulta de puericultura específica pelo seu ID")
    public ResponseEntity<ConsultaPuericulturaResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/consultas-puericultura/{}", id);
        try {
            ConsultaPuericulturaResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Consulta de puericultura não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar consulta de puericultura", description = "Atualiza uma consulta de puericultura existente")
    public ResponseEntity<ConsultaPuericulturaResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody ConsultaPuericulturaRequest request) {
        log.debug("REQUEST PUT /v1/consultas-puericultura/{} - payload: {}", id, request);
        ConsultaPuericulturaResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir consulta de puericultura", description = "Exclui (desativa) uma consulta de puericultura do sistema")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/consultas-puericultura/{}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

