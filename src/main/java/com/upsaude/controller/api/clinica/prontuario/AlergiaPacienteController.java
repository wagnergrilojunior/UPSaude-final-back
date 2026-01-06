package com.upsaude.controller.api.clinica.prontuario;

import com.upsaude.api.request.clinica.prontuario.AlergiaPacienteRequest;
import com.upsaude.api.response.clinica.prontuario.AlergiaPacienteResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.clinica.prontuario.AlergiaPacienteService;
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
@RequestMapping("/v1/prontuarios/{prontuarioId}/alergias")
@Tag(name = "Alergias do Paciente", description = "API para gerenciamento de alergias do paciente no prontuário")
@RequiredArgsConstructor
@Slf4j
public class AlergiaPacienteController {

    private final AlergiaPacienteService alergiaPacienteService;

    @PostMapping
    @Operation(summary = "Criar nova alergia", description = "Cria uma nova alergia para o paciente no prontuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alergia criada com sucesso",
                    content = @Content(schema = @Schema(implementation = AlergiaPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiaPacienteResponse> criar(
            @Parameter(description = "ID do prontuário", required = true)
            @PathVariable UUID prontuarioId,
            @Valid @RequestBody AlergiaPacienteRequest request) {
        log.debug("REQUEST POST /v1/prontuarios/{}/alergias - payload: {}", prontuarioId, request);
        try {
            request.setProntuario(prontuarioId);
            AlergiaPacienteResponse response = alergiaPacienteService.criar(request);
            log.info("Alergia criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao criar alergia — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar alergia — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter alergia por ID", description = "Retorna uma alergia específica pelo seu ID")
    public ResponseEntity<AlergiaPacienteResponse> obterPorId(
            @Parameter(description = "ID do prontuário", required = true)
            @PathVariable UUID prontuarioId,
            @Parameter(description = "ID da alergia", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/prontuarios/{}/alergias/{}", prontuarioId, id);
        try {
            AlergiaPacienteResponse response = alergiaPacienteService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Alergia não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter alergia por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar alergias do prontuário", description = "Retorna uma lista paginada de alergias do prontuário")
    public ResponseEntity<Page<AlergiaPacienteResponse>> listar(
            @Parameter(description = "ID do prontuário", required = true)
            @PathVariable UUID prontuarioId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/prontuarios/{}/alergias", prontuarioId);
        try {
            Page<AlergiaPacienteResponse> response = alergiaPacienteService.listarPorProntuario(prontuarioId, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar alergias do prontuário — prontuarioId: {}", prontuarioId, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar alergia", description = "Atualiza uma alergia existente")
    public ResponseEntity<AlergiaPacienteResponse> atualizar(
            @Parameter(description = "ID do prontuário", required = true)
            @PathVariable UUID prontuarioId,
            @Parameter(description = "ID da alergia", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody AlergiaPacienteRequest request) {
        log.debug("REQUEST PUT /v1/prontuarios/{}/alergias/{}", prontuarioId, id);
        try {
            request.setProntuario(prontuarioId);
            AlergiaPacienteResponse response = alergiaPacienteService.atualizar(id, request);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar alergia — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar alergia — ID: {}", id, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir alergia", description = "Exclui permanentemente uma alergia")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do prontuário", required = true)
            @PathVariable UUID prontuarioId,
            @Parameter(description = "ID da alergia", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/prontuarios/{}/alergias/{}", prontuarioId, id);
        try {
            alergiaPacienteService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Alergia não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir alergia — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar alergia", description = "Inativa uma alergia sem excluí-la")
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do prontuário", required = true)
            @PathVariable UUID prontuarioId,
            @Parameter(description = "ID da alergia", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/prontuarios/{}/alergias/{}/inativar", prontuarioId, id);
        try {
            alergiaPacienteService.inativar(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Alergia não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar alergia — ID: {}", id, ex);
            throw ex;
        }
    }
}

