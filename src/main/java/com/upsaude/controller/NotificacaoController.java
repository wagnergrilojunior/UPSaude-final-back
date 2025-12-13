package com.upsaude.controller;

import com.upsaude.api.request.NotificacaoRequest;
import com.upsaude.api.response.NotificacaoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.NotificacaoService;
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

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/v1/notificacoes")
@Tag(name = "Notificações", description = "API para gerenciamento de notificações")
@RequiredArgsConstructor
@Slf4j
public class NotificacaoController {

    private final NotificacaoService service;

    @PostMapping
    @Operation(summary = "Criar notificação", description = "Cria uma nova notificação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notificação criada com sucesso",
            content = @Content(schema = @Schema(implementation = NotificacaoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<NotificacaoResponse> criar(@Valid @RequestBody NotificacaoRequest request) {
        log.debug("REQUEST POST /v1/notificacoes - payload: {}", request);
        try {
            NotificacaoResponse response = service.criar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar notificação — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar notificações", description = "Retorna uma lista paginada de notificações")
    public ResponseEntity<Page<NotificacaoResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable,
        @RequestParam(required = false) UUID estabelecimentoId,
        @RequestParam(required = false) UUID pacienteId,
        @RequestParam(required = false) UUID profissionalId,
        @RequestParam(required = false) UUID agendamentoId,
        @RequestParam(required = false) String statusEnvio,
        @RequestParam(required = false) OffsetDateTime inicio,
        @RequestParam(required = false) OffsetDateTime fim,
        @RequestParam(required = false) Boolean usarPrevista) {
        log.debug("REQUEST GET /v1/notificacoes - pageable: {}, estabelecimentoId: {}, pacienteId: {}, profissionalId: {}, agendamentoId: {}, statusEnvio: {}, inicio: {}, fim: {}, usarPrevista: {}",
            pageable, estabelecimentoId, pacienteId, profissionalId, agendamentoId, statusEnvio, inicio, fim, usarPrevista);
        Page<NotificacaoResponse> response = service.listar(pageable, estabelecimentoId, pacienteId, profissionalId, agendamentoId, statusEnvio, inicio, fim, usarPrevista);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter notificação por ID", description = "Retorna uma notificação pelo ID")
    public ResponseEntity<NotificacaoResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/notificacoes/{}", id);
        try {
            NotificacaoResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Notificação não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar notificação", description = "Atualiza uma notificação existente")
    public ResponseEntity<NotificacaoResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody NotificacaoRequest request) {
        log.debug("REQUEST PUT /v1/notificacoes/{} - payload: {}", id, request);
        NotificacaoResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir notificação", description = "Exclui (desativa) uma notificação")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/notificacoes/{}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

