package com.upsaude.controller.api.clinica.prontuario;

import com.upsaude.api.response.clinica.prontuario.ProntuarioResumoResponse;
import com.upsaude.api.response.clinica.prontuario.ProntuarioTimelineResponse;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.clinica.prontuario.ProntuarioQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/v1/pacientes/{pacienteId}/prontuario")
@Tag(name = "Prontuário", description = "API para consulta de prontuário (somente leitura)")
@RequiredArgsConstructor
@Slf4j
public class ProntuarioController {

    private final ProntuarioQueryService prontuarioQueryService;

    @GetMapping
    @Operation(summary = "Obter timeline do prontuário", description = "Retorna timeline completa do prontuário do paciente (atendimentos, consultas, receitas, dispensações)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Timeline retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = ProntuarioTimelineResponse.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProntuarioTimelineResponse> obterTimeline(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId) {
        log.debug("REQUEST GET /v1/pacientes/{}/prontuario", pacienteId);
        try {
            ProntuarioTimelineResponse response = prontuarioQueryService.buscarTimeline(pacienteId);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Paciente não encontrado — ID: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter timeline do prontuário — pacienteId: {}", pacienteId, ex);
            throw ex;
        }
    }

    @GetMapping("/resumo")
    @Operation(summary = "Obter resumo do prontuário", description = "Retorna resumo clínico do paciente (últimos eventos, principais diagnósticos, prescrições recentes)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resumo retornado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProntuarioResumoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProntuarioResumoResponse> obterResumo(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId) {
        log.debug("REQUEST GET /v1/pacientes/{}/prontuario/resumo", pacienteId);
        try {
            ProntuarioResumoResponse response = prontuarioQueryService.buscarResumo(pacienteId);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Paciente não encontrado — ID: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter resumo do prontuário — pacienteId: {}", pacienteId, ex);
            throw ex;
        }
    }

    @GetMapping("/eventos")
    @Operation(summary = "Listar eventos do prontuário", description = "Retorna uma lista paginada e filtrável de eventos do prontuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ProntuarioTimelineResponse.ProntuarioEventoResponse>> listarEventos(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId,
            @Parameter(description = "Tipo de registro para filtrar (ATENDIMENTO, CONSULTA, RECEITA, DISPENSACAO)")
            @RequestParam(required = false) String tipoRegistro,
            @Parameter(description = "Data de início para filtrar eventos")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
            @Parameter(description = "Data de fim para filtrar eventos")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataFim,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/pacientes/{}/prontuario/eventos - tipoRegistro: {}, dataInicio: {}, dataFim: {}", 
                pacienteId, tipoRegistro, dataInicio, dataFim);
        try {
            Page<ProntuarioTimelineResponse.ProntuarioEventoResponse> response = 
                    prontuarioQueryService.buscarEventos(pacienteId, tipoRegistro, dataInicio, dataFim, pageable);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Paciente não encontrado — ID: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar eventos do prontuário — pacienteId: {}", pacienteId, ex);
            throw ex;
        }
    }
}

