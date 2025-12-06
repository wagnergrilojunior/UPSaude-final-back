package com.upsaude.controller;

import com.upsaude.api.request.MedicacoesContinuasRequest;
import com.upsaude.api.response.MedicacoesContinuasResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.MedicacoesContinuasService;
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
 * Controlador REST para operações relacionadas a Medicações Contínuas.
 *
 * @author UPSaúde
 */
@Slf4j
@RestController
@RequestMapping("/v1/medicacoes-continuas")
@Tag(name = "Medicações Contínuas", description = "API para gerenciamento de Medicações Contínuas")
@RequiredArgsConstructor
public class MedicacoesContinuasController {

    private final MedicacoesContinuasService medicacoesContinuasService;

    @PostMapping
    @Operation(summary = "Criar nova medicação contínua", description = "Cria uma nova medicação contínua no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medicação contínua criada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacoesContinuasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacoesContinuasResponse> criar(@Valid @RequestBody MedicacoesContinuasRequest request) {
        log.debug("REQUEST POST /v1/medicacoes-continuas - payload: {}", request);
        try {
            MedicacoesContinuasResponse response = medicacoesContinuasService.criar(request);
            log.info("Medicação contínua criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar medicação contínua — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar medicação contínua — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar medicações contínuas", description = "Retorna uma lista paginada de medicações contínuas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medicações contínuas retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MedicacoesContinuasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/medicacoes-continuas - pageable: {}", pageable);
        try {
            Page<MedicacoesContinuasResponse> response = medicacoesContinuasService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar medicações contínuas — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter medicação contínua por ID", description = "Retorna uma medicação contínua específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicação contínua encontrada",
                    content = @Content(schema = @Schema(implementation = MedicacoesContinuasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Medicação contínua não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacoesContinuasResponse> obterPorId(
            @Parameter(description = "ID da medicação contínua", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/medicacoes-continuas/{}", id);
        try {
            MedicacoesContinuasResponse response = medicacoesContinuasService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Medicação contínua não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter medicação contínua por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medicação contínua", description = "Atualiza uma medicação contínua existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicação contínua atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacoesContinuasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Medicação contínua não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacoesContinuasResponse> atualizar(
            @Parameter(description = "ID da medicação contínua", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody MedicacoesContinuasRequest request) {
        log.debug("REQUEST PUT /v1/medicacoes-continuas/{} - payload: {}", id, request);
        try {
            MedicacoesContinuasResponse response = medicacoesContinuasService.atualizar(id, request);
            log.info("Medicação contínua atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar medicação contínua — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar medicação contínua — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir medicação contínua", description = "Exclui (desativa) uma medicação contínua do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medicação contínua excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Medicação contínua não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da medicação contínua", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/medicacoes-continuas/{}", id);
        try {
            medicacoesContinuasService.excluir(id);
            log.info("Medicação contínua excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir medicação contínua — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir medicação contínua — ID: {}", id, ex);
            throw ex;
        }
    }
}

