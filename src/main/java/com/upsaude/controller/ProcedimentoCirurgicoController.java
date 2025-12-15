package com.upsaude.controller;

import com.upsaude.api.request.ProcedimentoCirurgicoRequest;
import com.upsaude.api.response.ProcedimentoCirurgicoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.ProcedimentoCirurgicoService;
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

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/procedimentos-cirurgicos")
@Tag(name = "Procedimentos Cirúrgicos", description = "API para gerenciamento de procedimentos cirúrgicos")
@RequiredArgsConstructor
public class ProcedimentoCirurgicoController {

    private final ProcedimentoCirurgicoService service;

    @PostMapping
    @Operation(summary = "Criar procedimento cirúrgico")
    public ResponseEntity<ProcedimentoCirurgicoResponse> criar(@Valid @RequestBody ProcedimentoCirurgicoRequest request) {
        log.debug("REQUEST POST /v1/procedimentos-cirurgicos - payload: {}", request);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar procedimento cirúrgico — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter procedimento cirúrgico por ID")
    public ResponseEntity<ProcedimentoCirurgicoResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/procedimentos-cirurgicos/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Procedimento cirúrgico não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar procedimentos cirúrgicos")
    public ResponseEntity<Page<ProcedimentoCirurgicoResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable,
        @RequestParam(required = false) UUID cirurgiaId,
        @RequestParam(required = false) String codigoProcedimento
    ) {
        log.debug("REQUEST GET /v1/procedimentos-cirurgicos - pageable: {}, cirurgiaId: {}, codigoProcedimento: {}", pageable, cirurgiaId, codigoProcedimento);
        return ResponseEntity.ok(service.listar(pageable, cirurgiaId, codigoProcedimento));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar procedimento cirúrgico")
    public ResponseEntity<ProcedimentoCirurgicoResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody ProcedimentoCirurgicoRequest request) {
        log.debug("REQUEST PUT /v1/procedimentos-cirurgicos/{} - payload: {}", id, request);
        try {
            return ResponseEntity.ok(service.atualizar(id, request));
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar procedimento cirúrgico — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir procedimento cirúrgico")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/procedimentos-cirurgicos/{}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

