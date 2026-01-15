package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.CompetenciaFechamentoRequest;
import com.upsaude.api.request.financeiro.FinanceiroOperacaoMotivoRequest;
import com.upsaude.api.response.financeiro.CompetenciaFechamentoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.CompetenciaFechamentoService;
import com.upsaude.service.api.financeiro.FinanceiroIntegrationService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/financeiro/operacoes")
@Tag(name = "Financeiro - Operações", description = "API de ações explícitas para orquestração do financeiro (reserva/consumo/estorno/fechamento)")
@RequiredArgsConstructor
@Slf4j
public class FinanceiroOperacoesController {

    private final FinanceiroIntegrationService financeiroIntegrationService;
    private final CompetenciaFechamentoService competenciaFechamentoService;
    private final TenantService tenantService;

    @PostMapping("/agendamentos/{agendamentoId}/reservar")
    @Operation(
            summary = "Reservar orçamento para um agendamento",
            description = "Força a reserva orçamentária para um agendamento (normalmente automático no fluxo de confirmação)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> reservarAgendamento(
            @Parameter(description = "ID do agendamento", required = true)
            @PathVariable UUID agendamentoId
    ) {
        log.debug("REQUEST POST /api/v1/financeiro/operacoes/agendamentos/{}/reservar", agendamentoId);
        try {
            financeiroIntegrationService.reservarOrcamento(agendamentoId);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao reservar orçamento para agendamento — agendamentoId: {}, mensagem: {}", agendamentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao reservar orçamento para agendamento — agendamentoId: {}", agendamentoId, ex);
            throw ex;
        }
    }

    @PostMapping("/agendamentos/{agendamentoId}/estornar")
    @Operation(
            summary = "Estornar reserva de um agendamento",
            description = "Força estorno (liberação) da reserva orçamentária de um agendamento (normalmente automático em cancelamento/no-show)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estorno realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> estornarReservaAgendamento(
            @Parameter(description = "ID do agendamento", required = true)
            @PathVariable UUID agendamentoId,
            @Valid @RequestBody FinanceiroOperacaoMotivoRequest request
    ) {
        log.debug("REQUEST POST /api/v1/financeiro/operacoes/agendamentos/{}/estornar - payload: {}", agendamentoId, request);
        try {
            financeiroIntegrationService.estornarReserva(agendamentoId, request.getMotivo());
            return ResponseEntity.noContent().build();
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao estornar reserva do agendamento — agendamentoId: {}, mensagem: {}, payload: {}", agendamentoId, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao estornar reserva do agendamento — agendamentoId: {}, payload: {}", agendamentoId, request, ex);
            throw ex;
        }
    }

    @PostMapping("/atendimentos/{atendimentoId}/consumir")
    @Operation(
            summary = "Consumir reserva para um atendimento",
            description = "Força o consumo da reserva orçamentária no momento do atendimento (normalmente automático ao concluir o atendimento)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consumo realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> consumirAtendimento(
            @Parameter(description = "ID do atendimento", required = true)
            @PathVariable UUID atendimentoId
    ) {
        log.debug("REQUEST POST /api/v1/financeiro/operacoes/atendimentos/{}/consumir", atendimentoId);
        try {
            financeiroIntegrationService.consumirReserva(atendimentoId);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao consumir reserva para atendimento — atendimentoId: {}, mensagem: {}", atendimentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao consumir reserva para atendimento — atendimentoId: {}", atendimentoId, ex);
            throw ex;
        }
    }

    @PostMapping("/atendimentos/{atendimentoId}/estornar")
    @Operation(
            summary = "Estornar consumo de um atendimento",
            description = "Força estorno do consumo do atendimento (normalmente automático em cancelamentos/ajustes)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estorno realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> estornarAtendimento(
            @Parameter(description = "ID do atendimento", required = true)
            @PathVariable UUID atendimentoId,
            @Valid @RequestBody FinanceiroOperacaoMotivoRequest request
    ) {
        log.debug("REQUEST POST /api/v1/financeiro/operacoes/atendimentos/{}/estornar - payload: {}", atendimentoId, request);
        try {
            financeiroIntegrationService.estornarConsumo(atendimentoId, request.getMotivo());
            return ResponseEntity.noContent().build();
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao estornar consumo do atendimento — atendimentoId: {}, mensagem: {}, payload: {}", atendimentoId, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao estornar consumo do atendimento — atendimentoId: {}, payload: {}", atendimentoId, request, ex);
            throw ex;
        }
    }

    @PostMapping("/competencias/{competenciaFinanceiraId}/fechar")
    @Operation(
            summary = "Fechar competência financeira",
            description = "Dispara o fechamento da competência financeira (base para BPA e travas de integridade)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Competência fechada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Competência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CompetenciaFechamentoResponse> fecharCompetencia(
            @Parameter(description = "ID da competência financeira", required = true)
            @PathVariable UUID competenciaFinanceiraId,
            @Valid @RequestBody(required = false) CompetenciaFechamentoRequest request
    ) {
        log.debug("REQUEST POST /api/v1/financeiro/operacoes/competencias/{}/fechar", competenciaFinanceiraId);
        try {
            UUID tenantId = tenantService.validarTenantAtual();

            if (request == null) {
                request = CompetenciaFechamentoRequest.builder().build();
            }

            CompetenciaFechamentoResponse response = competenciaFechamentoService.fecharCompetencia(
                    competenciaFinanceiraId, tenantId, request);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao fechar competência financeira — competenciaFinanceiraId: {}, mensagem: {}", competenciaFinanceiraId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao fechar competência financeira — competenciaFinanceiraId: {}", competenciaFinanceiraId, ex);
            throw ex;
        }
    }
}

