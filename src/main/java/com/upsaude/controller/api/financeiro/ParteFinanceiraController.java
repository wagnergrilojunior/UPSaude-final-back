package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.ParteFinanceiraRequest;
import com.upsaude.api.response.financeiro.ParteFinanceiraResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.ParteFinanceiraService;
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
@RequestMapping("/api/v1/financeiro/partes-financeiras")
@Tag(name = "Financeiro - Partes Financeiras", description = "API para gerenciamento de Partes Financeiras")
@RequiredArgsConstructor
@Slf4j
public class ParteFinanceiraController {

    private final ParteFinanceiraService service;

    @PostMapping
    @Operation(summary = "Criar parte financeira", description = "Cria uma nova parte financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Parte criada com sucesso",
                    content = @Content(schema = @Schema(implementation = ParteFinanceiraResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ParteFinanceiraResponse> criar(@Valid @RequestBody ParteFinanceiraRequest request) {
        log.debug("REQUEST POST /api/v1/financeiro/partes-financeiras - payload: {}", request);
        try {
            ParteFinanceiraResponse response = service.criar(request);
            log.info("Parte financeira criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar parte financeira — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar parte financeira — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar partes financeiras", description = "Retorna uma lista paginada de partes financeiras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ParteFinanceiraResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /api/v1/financeiro/partes-financeiras - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar partes financeiras — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter parte por ID", description = "Retorna uma parte financeira específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parte encontrada",
                    content = @Content(schema = @Schema(implementation = ParteFinanceiraResponse.class))),
            @ApiResponse(responseCode = "404", description = "Parte não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ParteFinanceiraResponse> obterPorId(
            @Parameter(description = "ID da parte", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /api/v1/financeiro/partes-financeiras/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Parte não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter parte financeira — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar parte", description = "Atualiza uma parte financeira existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parte atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = ParteFinanceiraResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Parte não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ParteFinanceiraResponse> atualizar(
            @Parameter(description = "ID da parte", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ParteFinanceiraRequest request) {
        log.debug("REQUEST PUT /api/v1/financeiro/partes-financeiras/{} - payload: {}", id, request);
        try {
            ParteFinanceiraResponse response = service.atualizar(id, request);
            log.info("Parte financeira atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar parte — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar parte — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir parte", description = "Exclui uma parte financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Parte excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Parte não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da parte", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /api/v1/financeiro/partes-financeiras/{}", id);
        try {
            service.excluir(id);
            log.info("Parte financeira excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Parte não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir parte financeira — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar parte", description = "Inativa uma parte financeira")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Parte inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Parte não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da parte", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /api/v1/financeiro/partes-financeiras/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Parte financeira inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Parte não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar parte financeira — ID: {}", id, ex);
            throw ex;
        }
    }
}

