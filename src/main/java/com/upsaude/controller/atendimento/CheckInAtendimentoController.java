package com.upsaude.controller.atendimento;

import com.upsaude.entity.atendimento.Atendimento;

import com.upsaude.api.request.atendimento.CheckInAtendimentoRequest;
import com.upsaude.api.response.atendimento.CheckInAtendimentoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.atendimento.CheckInAtendimentoService;
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
@RequestMapping("/v1/checkin-atendimento")
@Tag(name = "Check-in Atendimento", description = "API para gerenciamento de Check-ins de Atendimento")
@RequiredArgsConstructor
@Slf4j
public class CheckInAtendimentoController {

    private final CheckInAtendimentoService service;

    @PostMapping
    @Operation(summary = "Criar novo check-in", description = "Cria um novo check-in de atendimento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Check-in criado com sucesso",
            content = @Content(schema = @Schema(implementation = CheckInAtendimentoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CheckInAtendimentoResponse> criar(@Valid @RequestBody CheckInAtendimentoRequest request) {
        log.debug("REQUEST POST /v1/checkin-atendimento - payload: {}", request);
        CheckInAtendimentoResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar check-ins", description = "Retorna uma lista paginada de check-ins")
    public ResponseEntity<Page<CheckInAtendimentoResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable) {
        log.debug("REQUEST GET /v1/checkin-atendimento - pageable: {}", pageable);
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter check-in por ID", description = "Retorna um check-in específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Check-in encontrado",
            content = @Content(schema = @Schema(implementation = CheckInAtendimentoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Check-in não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CheckInAtendimentoResponse> obterPorId(
        @Parameter(description = "ID do check-in", required = true) @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/checkin-atendimento/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Check-in não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar check-in", description = "Atualiza um check-in existente")
    public ResponseEntity<CheckInAtendimentoResponse> atualizar(
        @PathVariable UUID id,
        @Valid @RequestBody CheckInAtendimentoRequest request) {
        log.debug("REQUEST PUT /v1/checkin-atendimento/{} - payload: {}", id, request);
        try {
            return ResponseEntity.ok(service.atualizar(id, request));
        } catch (BadRequestException ex) {
            log.warn("Falha ao atualizar check-in — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir check-in", description = "Exclui (desativa) um check-in")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/checkin-atendimento/{}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
