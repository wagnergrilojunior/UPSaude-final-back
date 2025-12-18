package com.upsaude.controller.atendimento;

import com.upsaude.entity.atendimento.Consultas;

import com.upsaude.api.request.atendimento.ConsultaPreNatalRequest;
import com.upsaude.api.response.atendimento.ConsultaPreNatalResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.atendimento.ConsultaPreNatalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Slf4j
@RestController
@RequestMapping("/v1/consultas-pre-natal")
@Tag(name = "Consultas Pré-Natal", description = "API para gerenciamento de consultas do pré-natal")
@RequiredArgsConstructor
public class ConsultaPreNatalController {

    private final ConsultaPreNatalService service;

    @PostMapping
    @Operation(summary = "Criar consulta pré-natal")
    public ResponseEntity<ConsultaPreNatalResponse> criar(@Valid @RequestBody ConsultaPreNatalRequest request) {
        log.debug("REQUEST POST /v1/consultas-pre-natal - payload: {}", request);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar consulta pré-natal — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter consulta pré-natal por ID")
    public ResponseEntity<ConsultaPreNatalResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/consultas-pre-natal/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Consulta pré-natal não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar consultas pré-natal")
    public ResponseEntity<Page<ConsultaPreNatalResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable,
        @RequestParam(required = false) UUID preNatalId,
        @RequestParam(required = false) UUID estabelecimentoId,
        @RequestParam(required = false) OffsetDateTime inicio,
        @RequestParam(required = false) OffsetDateTime fim
    ) {
        log.debug("REQUEST GET /v1/consultas-pre-natal - pageable: {}, preNatalId: {}, estabelecimentoId: {}, inicio: {}, fim: {}",
            pageable, preNatalId, estabelecimentoId, inicio, fim);
        return ResponseEntity.ok(service.listar(pageable, preNatalId, estabelecimentoId, inicio, fim));
    }

    @GetMapping("/pre-natal/{preNatalId}/count")
    @Operation(summary = "Contar consultas por pré-natal")
    public ResponseEntity<Long> countPorPreNatal(@PathVariable UUID preNatalId) {
        log.debug("REQUEST GET /v1/consultas-pre-natal/pre-natal/{}/count", preNatalId);
        return ResponseEntity.ok(service.countPorPreNatalId(preNatalId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar consulta pré-natal")
    public ResponseEntity<ConsultaPreNatalResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody ConsultaPreNatalRequest request) {
        log.debug("REQUEST PUT /v1/consultas-pre-natal/{} - payload: {}", id, request);
        try {
            return ResponseEntity.ok(service.atualizar(id, request));
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar consulta pré-natal — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir consulta pré-natal")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/consultas-pre-natal/{}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

