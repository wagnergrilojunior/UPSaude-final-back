package com.upsaude.controller;

import com.upsaude.api.request.AgendamentoRequest;
import com.upsaude.api.response.AgendamentoResponse;
import com.upsaude.enums.StatusAgendamentoEnum;
import com.upsaude.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a Agendamento.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/agendamentos")
@Tag(name = "Agendamentos", description = "API para gerenciamento de Agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    @Operation(summary = "Criar novo agendamento", description = "Cria um novo agendamento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AgendamentoResponse> criar(@Valid @RequestBody AgendamentoRequest request) {
        AgendamentoResponse response = agendamentoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar agendamentos", description = "Retorna uma lista paginada de agendamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AgendamentoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AgendamentoResponse> response = agendamentoService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar agendamentos por paciente", description = "Retorna uma lista paginada de agendamentos de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos do paciente retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do paciente inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AgendamentoResponse>> listarPorPaciente(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AgendamentoResponse> response = agendamentoService.listarPorPaciente(pacienteId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar agendamentos por profissional", description = "Retorna uma lista paginada de agendamentos de um profissional específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos do profissional retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do profissional inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AgendamentoResponse>> listarPorProfissional(
            @Parameter(description = "ID do profissional", required = true)
            @PathVariable UUID profissionalId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AgendamentoResponse> response = agendamentoService.listarPorProfissional(profissionalId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/medico/{medicoId}")
    @Operation(summary = "Listar agendamentos por médico", description = "Retorna uma lista paginada de agendamentos de um médico específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos do médico retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do médico inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AgendamentoResponse>> listarPorMedico(
            @Parameter(description = "ID do médico", required = true)
            @PathVariable UUID medicoId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AgendamentoResponse> response = agendamentoService.listarPorMedico(medicoId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar agendamentos por estabelecimento", description = "Retorna uma lista paginada de agendamentos de um estabelecimento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos do estabelecimento retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do estabelecimento inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AgendamentoResponse>> listarPorEstabelecimento(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID estabelecimentoId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AgendamentoResponse> response = agendamentoService.listarPorEstabelecimento(estabelecimentoId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar agendamentos por status", description = "Retorna uma lista paginada de agendamentos com um status específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AgendamentoResponse>> listarPorStatus(
            @Parameter(description = "Status do agendamento", required = true)
            @PathVariable StatusAgendamentoEnum status,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AgendamentoResponse> response = agendamentoService.listarPorStatus(status, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profissional/{profissionalId}/periodo")
    @Operation(summary = "Listar agendamentos por profissional e período", description = "Retorna uma lista paginada de agendamentos de um profissional em um período")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AgendamentoResponse>> listarPorProfissionalEPeriodo(
            @Parameter(description = "ID do profissional", required = true)
            @PathVariable UUID profissionalId,
            @Parameter(description = "Data de início do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
            @Parameter(description = "Data fim do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataFim,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AgendamentoResponse> response = agendamentoService.listarPorProfissionalEPeriodo(profissionalId, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}/periodo")
    @Operation(summary = "Listar agendamentos por estabelecimento e período", description = "Retorna uma lista paginada de agendamentos de um estabelecimento em um período")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AgendamentoResponse>> listarPorEstabelecimentoEPeriodo(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID estabelecimentoId,
            @Parameter(description = "Data de início do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
            @Parameter(description = "Data fim do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataFim,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AgendamentoResponse> response = agendamentoService.listarPorEstabelecimentoEPeriodo(estabelecimentoId, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profissional/{profissionalId}/status/{status}")
    @Operation(summary = "Listar agendamentos por profissional e status", description = "Retorna uma lista paginada de agendamentos de um profissional com um status específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AgendamentoResponse>> listarPorProfissionalEStatus(
            @Parameter(description = "ID do profissional", required = true)
            @PathVariable UUID profissionalId,
            @Parameter(description = "Status do agendamento", required = true)
            @PathVariable StatusAgendamentoEnum status,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AgendamentoResponse> response = agendamentoService.listarPorProfissionalEStatus(profissionalId, status, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter agendamento por ID", description = "Retorna um agendamento específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AgendamentoResponse> obterPorId(
            @Parameter(description = "ID do agendamento", required = true)
            @PathVariable UUID id) {
        AgendamentoResponse response = agendamentoService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar agendamento", description = "Atualiza um agendamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AgendamentoResponse> atualizar(
            @Parameter(description = "ID do agendamento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody AgendamentoRequest request) {
        AgendamentoResponse response = agendamentoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar agendamento", description = "Cancela um agendamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento cancelado com sucesso",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Agendamento já está cancelado ou dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AgendamentoResponse> cancelar(
            @Parameter(description = "ID do agendamento", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Motivo do cancelamento")
            @RequestParam(required = false) String motivoCancelamento) {
        AgendamentoResponse response = agendamentoService.cancelar(id, motivoCancelamento);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/confirmar")
    @Operation(summary = "Confirmar agendamento", description = "Confirma um agendamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento confirmado com sucesso",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AgendamentoResponse> confirmar(
            @Parameter(description = "ID do agendamento", required = true)
            @PathVariable UUID id) {
        AgendamentoResponse response = agendamentoService.confirmar(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/reagendar")
    @Operation(summary = "Reagendar agendamento", description = "Reagenda um agendamento existente criando um novo agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento reagendado com sucesso",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AgendamentoResponse> reagendar(
            @Parameter(description = "ID do agendamento original", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody AgendamentoRequest novoAgendamentoRequest,
            @Parameter(description = "Motivo do reagendamento")
            @RequestParam(required = false) String motivoReagendamento) {
        AgendamentoResponse response = agendamentoService.reagendar(id, novoAgendamentoRequest, motivoReagendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir agendamento", description = "Exclui (desativa) um agendamento do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do agendamento", required = true)
            @PathVariable UUID id) {
        agendamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

