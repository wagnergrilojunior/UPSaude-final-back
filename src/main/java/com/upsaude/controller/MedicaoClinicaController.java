package com.upsaude.controller;

import com.upsaude.api.request.MedicaoClinicaRequest;
import com.upsaude.api.response.MedicaoClinicaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.MedicaoClinicaService;
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
 * Controlador REST para operações relacionadas a Medições Clínicas.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/medicoes-clinicas")
@Tag(name = "Medições Clínicas", description = "API para gerenciamento de Medições Clínicas")
@RequiredArgsConstructor
@Slf4j
public class MedicaoClinicaController {

    private final MedicaoClinicaService medicaoClinicaService;

    @PostMapping
    @Operation(summary = "Criar nova medição clínica", description = "Cria uma nova medição clínica no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medição clínica criada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicaoClinicaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicaoClinicaResponse> criar(@Valid @RequestBody MedicaoClinicaRequest request) {
        log.debug("REQUEST POST /v1/medicoes-clinicas - payload: {}", request);
        try {
            MedicaoClinicaResponse response = medicaoClinicaService.criar(request);
            log.info("Medição clínica criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar medição clínica — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar medição clínica — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar medições clínicas", description = "Retorna uma lista paginada de medições clínicas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medições clínicas retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MedicaoClinicaResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/medicoes-clinicas - pageable: {}", pageable);
        try {
            Page<MedicaoClinicaResponse> response = medicaoClinicaService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar medições clínicas — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar medições clínicas por paciente", description = "Retorna uma lista paginada de medições clínicas de um paciente específico, ordenadas por data/hora decrescente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medições clínicas do paciente retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do paciente inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MedicaoClinicaResponse>> listarPorPaciente(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/medicoes-clinicas/paciente/{} - pageable: {}", pacienteId, pageable);
        try {
            Page<MedicaoClinicaResponse> response = medicaoClinicaService.listarPorPaciente(pacienteId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar medições clínicas por paciente — pacienteId: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar medições clínicas por paciente — pacienteId: {}, pageable: {}", pacienteId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter medição clínica por ID", description = "Retorna uma medição clínica específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medição clínica encontrada",
                    content = @Content(schema = @Schema(implementation = MedicaoClinicaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Medição clínica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicaoClinicaResponse> obterPorId(
            @Parameter(description = "ID da medição clínica", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/medicoes-clinicas/{}", id);
        try {
            MedicaoClinicaResponse response = medicaoClinicaService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Medição clínica não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter medição clínica por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medição clínica", description = "Atualiza uma medição clínica existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medição clínica atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicaoClinicaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Medição clínica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicaoClinicaResponse> atualizar(
            @Parameter(description = "ID da medição clínica", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody MedicaoClinicaRequest request) {
        log.debug("REQUEST PUT /v1/medicoes-clinicas/{} - payload: {}", id, request);
        try {
            MedicaoClinicaResponse response = medicaoClinicaService.atualizar(id, request);
            log.info("Medição clínica atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar medição clínica — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar medição clínica — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir medição clínica", description = "Exclui (desativa) uma medição clínica do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medição clínica excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Medição clínica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da medição clínica", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/medicoes-clinicas/{}", id);
        try {
            medicaoClinicaService.excluir(id);
            log.info("Medição clínica excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Medição clínica não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir medição clínica — ID: {}", id, ex);
            throw ex;
        }
    }
}
