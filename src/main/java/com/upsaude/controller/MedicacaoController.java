package com.upsaude.controller;

import com.upsaude.api.request.MedicacaoRequest;
import com.upsaude.api.response.MedicacaoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.MedicacaoService;
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
@RequestMapping("/v1/medicacoes")
@Tag(name = "Medicações", description = "API para gerenciamento de Medicações")
@RequiredArgsConstructor
@Slf4j
public class MedicacaoController {

    private final MedicacaoService medicacaoService;

    @PostMapping
    @Operation(summary = "Criar nova medicação", description = "Cria uma nova medicação no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medicação criada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacaoResponse> criar(@Valid @RequestBody MedicacaoRequest request) {
        log.debug("REQUEST POST /v1/medicacoes - payload: {}", request);
        try {
            MedicacaoResponse response = medicacaoService.criar(request);
            log.info("Medicação criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar medicação — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar medicação — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar medicações", description = "Retorna uma lista paginada de medicações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medicações retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MedicacaoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/medicacoes - pageable: {}", pageable);
        try {
            Page<MedicacaoResponse> response = medicacaoService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar medicações — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter medicação por ID", description = "Retorna uma medicação específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicação encontrada",
                    content = @Content(schema = @Schema(implementation = MedicacaoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Medicação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacaoResponse> obterPorId(
            @Parameter(description = "ID da medicação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/medicacoes/{}", id);
        try {
            MedicacaoResponse response = medicacaoService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Medicação não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter medicação por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medicação", description = "Atualiza uma medicação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicação atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Medicação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacaoResponse> atualizar(
            @Parameter(description = "ID da medicação", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody MedicacaoRequest request) {
        log.debug("REQUEST PUT /v1/medicacoes/{} - payload: {}", id, request);
        try {
            MedicacaoResponse response = medicacaoService.atualizar(id, request);
            log.info("Medicação atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar medicação — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar medicação — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir medicação", description = "Exclui (desativa) uma medicação do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medicação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Medicação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da medicação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/medicacoes/{}", id);
        try {
            medicacaoService.excluir(id);
            log.info("Medicação excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Medicação não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir medicação — ID: {}", id, ex);
            throw ex;
        }
    }
}
