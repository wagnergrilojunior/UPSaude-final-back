package com.upsaude.controller;

import com.upsaude.api.request.MedicacoesContinuasPacienteRequest;
import com.upsaude.api.response.MedicacoesContinuasPacienteResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.MedicacoesContinuasPacienteService;
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
@RequestMapping("/v1/medicacoes-continuas-paciente")
@Tag(name = "Medicações Contínuas de Paciente", description = "API para gerenciamento de Medicações Contínuas de Paciente")
@RequiredArgsConstructor
@Slf4j
public class MedicacoesContinuasPacienteController {

    private final MedicacoesContinuasPacienteService medicacoesContinuasPacienteService;

    @PostMapping
    @Operation(summary = "Criar nova medicação contínua de paciente", description = "Cria uma nova medicação contínua de paciente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medicação contínua de paciente criada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacoesContinuasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacoesContinuasPacienteResponse> criar(@Valid @RequestBody MedicacoesContinuasPacienteRequest request) {
        log.debug("REQUEST POST /v1/medicacoes-continuas-paciente - payload: {}", request);
        try {
            MedicacoesContinuasPacienteResponse response = medicacoesContinuasPacienteService.criar(request);
            log.info("Medicação contínua de paciente criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar medicação contínua de paciente — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar medicação contínua de paciente — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar medicações contínuas de paciente", description = "Retorna uma lista paginada de medicações contínuas de paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medicações contínuas de paciente retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MedicacoesContinuasPacienteResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/medicacoes-continuas-paciente - pageable: {}", pageable);
        try {
            Page<MedicacoesContinuasPacienteResponse> response = medicacoesContinuasPacienteService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar medicações contínuas de paciente — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter medicação contínua de paciente por ID", description = "Retorna uma medicação contínua de paciente específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicação contínua de paciente encontrada",
                    content = @Content(schema = @Schema(implementation = MedicacoesContinuasPacienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Medicação contínua de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacoesContinuasPacienteResponse> obterPorId(
            @Parameter(description = "ID da medicação contínua de paciente", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/medicacoes-continuas-paciente/{}", id);
        try {
            MedicacoesContinuasPacienteResponse response = medicacoesContinuasPacienteService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Medicação contínua de paciente não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter medicação contínua de paciente por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medicação contínua de paciente", description = "Atualiza uma medicação contínua de paciente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicação contínua de paciente atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacoesContinuasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Medicação contínua de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacoesContinuasPacienteResponse> atualizar(
            @Parameter(description = "ID da medicação contínua de paciente", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody MedicacoesContinuasPacienteRequest request) {
        log.debug("REQUEST PUT /v1/medicacoes-continuas-paciente/{} - payload: {}", id, request);
        try {
            MedicacoesContinuasPacienteResponse response = medicacoesContinuasPacienteService.atualizar(id, request);
            log.info("Medicação contínua de paciente atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar medicação contínua de paciente — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar medicação contínua de paciente — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir medicação contínua de paciente", description = "Exclui (desativa) uma medicação contínua de paciente do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medicação contínua de paciente excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Medicação contínua de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da medicação contínua de paciente", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/medicacoes-continuas-paciente/{}", id);
        try {
            medicacoesContinuasPacienteService.excluir(id);
            log.info("Medicação contínua de paciente excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Medicação contínua de paciente não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir medicação contínua de paciente — ID: {}", id, ex);
            throw ex;
        }
    }
}
