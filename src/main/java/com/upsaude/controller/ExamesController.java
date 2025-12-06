package com.upsaude.controller;

import com.upsaude.api.request.ExamesRequest;
import com.upsaude.api.response.ExamesResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.ExamesService;
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

/**
 * Controlador REST para operações relacionadas a Exames.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/exames")
@Tag(name = "Exames", description = "API para gerenciamento de Exames")
@RequiredArgsConstructor
@Slf4j
public class ExamesController {

    private final ExamesService examesService;

    @PostMapping
    @Operation(summary = "Criar novo exame", description = "Cria um novo exame no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exame criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ExamesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ExamesResponse> criar(@Valid @RequestBody ExamesRequest request) {
        log.debug("REQUEST POST /v1/exames - payload: {}", request);
        try {
            ExamesResponse response = examesService.criar(request);
            log.info("Exame criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar exame — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar exame — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar exames", description = "Retorna uma lista paginada de exames")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de exames retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ExamesResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/exames - pageable: {}", pageable);
        try {
            Page<ExamesResponse> response = examesService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar exames — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter exame por ID", description = "Retorna um exame específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame encontrado",
                    content = @Content(schema = @Schema(implementation = ExamesResponse.class))),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ExamesResponse> obterPorId(
            @Parameter(description = "ID do exame", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/exames/{}", id);
        try {
            ExamesResponse response = examesService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Falha ao obter exame por ID — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter exame por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar exame", description = "Atualiza um exame existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ExamesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ExamesResponse> atualizar(
            @Parameter(description = "ID do exame", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ExamesRequest request) {
        log.debug("REQUEST PUT /v1/exames/{} - payload: {}", id, request);
        try {
            ExamesResponse response = examesService.atualizar(id, request);
            log.info("Exame atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar exame — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar exame — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir exame", description = "Exclui (desativa) um exame do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exame excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do exame", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/exames/{}", id);
        try {
            examesService.excluir(id);
            log.info("Exame excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir exame — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir exame — ID: {}", id, ex);
            throw ex;
        }
    }
}

