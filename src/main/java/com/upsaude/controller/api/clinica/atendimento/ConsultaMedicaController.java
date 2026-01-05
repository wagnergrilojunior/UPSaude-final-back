package com.upsaude.controller.api.clinica.atendimento;

import com.upsaude.api.request.clinica.atendimento.ConsultaCreateRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateAnamneseRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateAtestadoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateDiagnosticoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateEncaminhamentoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateExamesRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdatePrescricaoRequest;
import com.upsaude.api.response.clinica.atendimento.ConsultasResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.clinica.atendimento.ConsultasService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/v1/consultas")
@Tag(name = "Consultas Médicas", description = "API para gerenciamento de consultas médicas (ato clínico)")
@RequiredArgsConstructor
@Slf4j
public class ConsultaMedicaController {

    private final ConsultasService consultasService;

    @PostMapping
    @Operation(summary = "Criar nova consulta", description = "Cria uma nova consulta médica (pode vir com atendimentoId opcional)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consulta criada com sucesso",
                    content = @Content(schema = @Schema(implementation = ConsultasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConsultasResponse> criar(@Valid @RequestBody ConsultaCreateRequest request) {
        log.debug("REQUEST POST /v1/consultas - payload: {}", request);
        try {
            ConsultasResponse response = consultasService.criar(request);
            log.info("Consulta criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao criar consulta — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar consulta — payload: {}", request, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/anamnese")
    @Operation(summary = "Atualizar anamnese", description = "Atualiza a anamnese da consulta")
    public ResponseEntity<ConsultasResponse> atualizarAnamnese(
            @PathVariable UUID id,
            @Valid @RequestBody ConsultaUpdateAnamneseRequest request) {
        log.debug("REQUEST PUT /v1/consultas/{}/anamnese", id);
        try {
            ConsultasResponse response = consultasService.atualizarAnamnese(id, request);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar anamnese — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar anamnese — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/diagnostico")
    @Operation(summary = "Atualizar diagnóstico", description = "Atualiza o diagnóstico da consulta")
    public ResponseEntity<ConsultasResponse> atualizarDiagnostico(
            @PathVariable UUID id,
            @Valid @RequestBody ConsultaUpdateDiagnosticoRequest request) {
        log.debug("REQUEST PUT /v1/consultas/{}/diagnostico", id);
        try {
            ConsultasResponse response = consultasService.atualizarDiagnostico(id, request);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar diagnóstico — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar diagnóstico — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/prescricao")
    @Operation(summary = "Atualizar prescrição", description = "Atualiza a prescrição da consulta")
    public ResponseEntity<ConsultasResponse> atualizarPrescricao(
            @PathVariable UUID id,
            @Valid @RequestBody ConsultaUpdatePrescricaoRequest request) {
        log.debug("REQUEST PUT /v1/consultas/{}/prescricao", id);
        try {
            ConsultasResponse response = consultasService.atualizarPrescricao(id, request);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar prescrição — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar prescrição — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/exames")
    @Operation(summary = "Atualizar exames", description = "Atualiza os exames solicitados da consulta")
    public ResponseEntity<ConsultasResponse> atualizarExames(
            @PathVariable UUID id,
            @Valid @RequestBody ConsultaUpdateExamesRequest request) {
        log.debug("REQUEST PUT /v1/consultas/{}/exames", id);
        try {
            ConsultasResponse response = consultasService.atualizarExames(id, request);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar exames — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar exames — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/encaminhamento")
    @Operation(summary = "Atualizar encaminhamento", description = "Atualiza o encaminhamento da consulta")
    public ResponseEntity<ConsultasResponse> atualizarEncaminhamento(
            @PathVariable UUID id,
            @Valid @RequestBody ConsultaUpdateEncaminhamentoRequest request) {
        log.debug("REQUEST PUT /v1/consultas/{}/encaminhamento", id);
        try {
            ConsultasResponse response = consultasService.atualizarEncaminhamento(id, request);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar encaminhamento — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar encaminhamento — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/atestado")
    @Operation(summary = "Atualizar atestado", description = "Atualiza o atestado da consulta")
    public ResponseEntity<ConsultasResponse> atualizarAtestado(
            @PathVariable UUID id,
            @Valid @RequestBody ConsultaUpdateAtestadoRequest request) {
        log.debug("REQUEST PUT /v1/consultas/{}/atestado", id);
        try {
            ConsultasResponse response = consultasService.atualizarAtestado(id, request);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar atestado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar atestado — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/encerrar")
    @Operation(summary = "Encerrar consulta", description = "Encerra uma consulta, alterando status para CONCLUIDA e criando registro no prontuário")
    public ResponseEntity<ConsultasResponse> encerrar(@PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/consultas/{}/encerrar", id);
        try {
            ConsultasResponse response = consultasService.encerrar(id);
            log.info("Consulta encerrada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao encerrar consulta — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao encerrar consulta — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/pacientes/{pacienteId}/consultas")
    @Operation(summary = "Listar consultas do paciente", description = "Retorna uma lista paginada de consultas de um paciente específico")
    public ResponseEntity<Page<ConsultasResponse>> listarPorPaciente(
            @PathVariable UUID pacienteId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/consultas/pacientes/{}/consultas", pacienteId);
        try {
            Page<ConsultasResponse> response = consultasService.listarPorPaciente(pacienteId, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar consultas do paciente — pacienteId: {}", pacienteId, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter consulta por ID", description = "Retorna uma consulta específica pelo seu ID")
    public ResponseEntity<ConsultasResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/consultas/{}", id);
        try {
            ConsultasResponse response = consultasService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Consulta não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter consulta por ID — ID: {}", id, ex);
            throw ex;
        }
    }
}

