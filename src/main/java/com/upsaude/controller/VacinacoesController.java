package com.upsaude.controller;

import com.upsaude.api.request.VacinacoesRequest;
import com.upsaude.api.response.VacinacoesResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.VacinacoesService;
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
@RequestMapping("/v1/vacinacoes")
@Tag(name = "Vacinações", description = "API para gerenciamento de Vacinações")
@RequiredArgsConstructor
@Slf4j
public class VacinacoesController {

    private final VacinacoesService vacinacoesService;

    @PostMapping
    @Operation(summary = "Criar nova vacinação", description = "Cria uma nova vacinação no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vacinação criada com sucesso",
                    content = @Content(schema = @Schema(implementation = VacinacoesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VacinacoesResponse> criar(@Valid @RequestBody VacinacoesRequest request) {
        log.debug("REQUEST POST /v1/vacinacoes - payload: {}", request);
        try {
            VacinacoesResponse response = vacinacoesService.criar(request);
            log.info("Vacinação criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar vacinação — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar vacinação — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar vacinações", description = "Retorna uma lista paginada de vacinações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vacinações retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VacinacoesResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable,
            @RequestParam(required = false) UUID estabelecimentoId,
            @RequestParam(required = false) UUID pacienteId,
            @RequestParam(required = false) UUID vacinaId,
            @RequestParam(required = false) OffsetDateTime inicio,
            @RequestParam(required = false) OffsetDateTime fim) {
        log.debug("REQUEST GET /v1/vacinacoes - pageable: {}, estabelecimentoId: {}, pacienteId: {}, vacinaId: {}, inicio: {}, fim: {}",
            pageable, estabelecimentoId, pacienteId, vacinaId, inicio, fim);
        try {
            Page<VacinacoesResponse> response = vacinacoesService.listar(pageable, estabelecimentoId, pacienteId, vacinaId, inicio, fim);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar vacinações — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter vacinação por ID", description = "Retorna uma vacinação específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacinação encontrada",
                    content = @Content(schema = @Schema(implementation = VacinacoesResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vacinação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VacinacoesResponse> obterPorId(
            @Parameter(description = "ID da vacinação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/vacinacoes/{}", id);
        try {
            VacinacoesResponse response = vacinacoesService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Vacinação não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter vacinação por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar vacinação", description = "Atualiza uma vacinação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacinação atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = VacinacoesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Vacinação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VacinacoesResponse> atualizar(
            @Parameter(description = "ID da vacinação", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody VacinacoesRequest request) {
        log.debug("REQUEST PUT /v1/vacinacoes/{} - payload: {}", id, request);
        try {
            VacinacoesResponse response = vacinacoesService.atualizar(id, request);
            log.info("Vacinação atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar vacinação — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar vacinação — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir vacinação", description = "Exclui (desativa) uma vacinação do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vacinação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vacinação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da vacinação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/vacinacoes/{}", id);
        try {
            vacinacoesService.excluir(id);
            log.info("Vacinação excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Vacinação não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir vacinação — ID: {}", id, ex);
            throw ex;
        }
    }
}
