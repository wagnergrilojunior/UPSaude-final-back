package com.upsaude.controller.agendamento;

import com.upsaude.entity.agendamento.Agendamento;

import com.upsaude.api.request.agendamento.AgendamentoRequest;
import com.upsaude.api.response.agendamento.AgendamentoResponse;
import com.upsaude.enums.StatusAgendamentoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.agendamento.AgendamentoService;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/v1/agendamentos")
@Tag(name = "Agendamentos", description = "API para gerenciamento de Agendamentos")
@RequiredArgsConstructor
@Slf4j
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
        log.debug("REQUEST POST /v1/agendamentos - payload: {}", request);
        try {
            AgendamentoResponse response = agendamentoService.criar(request);
            log.info("Agendamento criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar agendamento — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar agendamento — payload: {}", request, ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/agendamentos - pageable: {}", pageable);
        try {
            Page<AgendamentoResponse> response = agendamentoService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar agendamentos — pageable: {}", pageable, ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/agendamentos/paciente/{} - pageable: {}", pacienteId, pageable);
        try {
            Page<AgendamentoResponse> response = agendamentoService.listarPorPaciente(pacienteId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar agendamentos por paciente — pacienteId: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar agendamentos por paciente — pacienteId: {}, pageable: {}", pacienteId, pageable, ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/agendamentos/profissional/{} - pageable: {}", profissionalId, pageable);
        try {
            Page<AgendamentoResponse> response = agendamentoService.listarPorProfissional(profissionalId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar agendamentos por profissional — profissionalId: {}, mensagem: {}", profissionalId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar agendamentos por profissional — profissionalId: {}, pageable: {}", profissionalId, pageable, ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/agendamentos/medico/{} - pageable: {}", medicoId, pageable);
        try {
            Page<AgendamentoResponse> response = agendamentoService.listarPorMedico(medicoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar agendamentos por médico — medicoId: {}, mensagem: {}", medicoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar agendamentos por médico — medicoId: {}, pageable: {}", medicoId, pageable, ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/agendamentos/estabelecimento/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<AgendamentoResponse> response = agendamentoService.listarPorEstabelecimento(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar agendamentos por estabelecimento — estabelecimentoId: {}, mensagem: {}", estabelecimentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar agendamentos por estabelecimento — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/agendamentos/status/{} - pageable: {}", status, pageable);
        try {
            Page<AgendamentoResponse> response = agendamentoService.listarPorStatus(status, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar agendamentos por status — status: {}, mensagem: {}", status, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar agendamentos por status — status: {}, pageable: {}", status, pageable, ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/agendamentos/profissional/{}/periodo - dataInicio: {}, dataFim: {}, pageable: {}", profissionalId, dataInicio, dataFim, pageable);
        try {
            Page<AgendamentoResponse> response = agendamentoService.listarPorProfissionalEPeriodo(profissionalId, dataInicio, dataFim, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar agendamentos por profissional e período — profissionalId: {}, dataInicio: {}, dataFim: {}, mensagem: {}", profissionalId, dataInicio, dataFim, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar agendamentos por profissional e período — profissionalId: {}, dataInicio: {}, dataFim: {}, pageable: {}", profissionalId, dataInicio, dataFim, pageable, ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/agendamentos/estabelecimento/{}/periodo - dataInicio: {}, dataFim: {}, pageable: {}", estabelecimentoId, dataInicio, dataFim, pageable);
        try {
            Page<AgendamentoResponse> response = agendamentoService.listarPorEstabelecimentoEPeriodo(estabelecimentoId, dataInicio, dataFim, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar agendamentos por estabelecimento e período — estabelecimentoId: {}, dataInicio: {}, dataFim: {}, mensagem: {}", estabelecimentoId, dataInicio, dataFim, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar agendamentos por estabelecimento e período — estabelecimentoId: {}, dataInicio: {}, dataFim: {}, pageable: {}", estabelecimentoId, dataInicio, dataFim, pageable, ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/agendamentos/profissional/{}/status/{} - pageable: {}", profissionalId, status, pageable);
        try {
            Page<AgendamentoResponse> response = agendamentoService.listarPorProfissionalEStatus(profissionalId, status, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar agendamentos por profissional e status — profissionalId: {}, status: {}, mensagem: {}", profissionalId, status, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar agendamentos por profissional e status — profissionalId: {}, status: {}, pageable: {}", profissionalId, status, pageable, ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/agendamentos/{}", id);
        try {
            AgendamentoResponse response = agendamentoService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Agendamento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter agendamento por ID — ID: {}", id, ex);
            throw ex;
        }
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
        log.debug("REQUEST PUT /v1/agendamentos/{} - payload: {}", id, request);
        try {
            AgendamentoResponse response = agendamentoService.atualizar(id, request);
            log.info("Agendamento atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar agendamento — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar agendamento — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
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
        log.debug("REQUEST POST /v1/agendamentos/{}/cancelar - motivoCancelamento: {}", id, motivoCancelamento);
        try {
            AgendamentoResponse response = agendamentoService.cancelar(id, motivoCancelamento);
            log.info("Agendamento cancelado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao cancelar agendamento — ID: {}, motivoCancelamento: {}, mensagem: {}", id, motivoCancelamento, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao cancelar agendamento — ID: {}, motivoCancelamento: {}", id, motivoCancelamento, ex);
            throw ex;
        }
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
        log.debug("REQUEST POST /v1/agendamentos/{}/confirmar", id);
        try {
            AgendamentoResponse response = agendamentoService.confirmar(id);
            log.info("Agendamento confirmado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao confirmar agendamento — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao confirmar agendamento — ID: {}", id, ex);
            throw ex;
        }
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
        log.debug("REQUEST POST /v1/agendamentos/{}/reagendar - novoAgendamentoRequest: {}, motivoReagendamento: {}", id, novoAgendamentoRequest, motivoReagendamento);
        try {
            AgendamentoResponse response = agendamentoService.reagendar(id, novoAgendamentoRequest, motivoReagendamento);
            log.info("Agendamento reagendado com sucesso. ID original: {}, ID novo: {}", id, response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao reagendar agendamento — ID: {}, motivoReagendamento: {}, mensagem: {}, payload: {}", id, motivoReagendamento, ex.getMessage(), novoAgendamentoRequest);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao reagendar agendamento — ID: {}, motivoReagendamento: {}, payload: {}", id, motivoReagendamento, novoAgendamentoRequest, ex);
            throw ex;
        }
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
        log.debug("REQUEST DELETE /v1/agendamentos/{}", id);
        try {
            agendamentoService.excluir(id);
            log.info("Agendamento excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Agendamento não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir agendamento — ID: {}", id, ex);
            throw ex;
        }
    }
}
