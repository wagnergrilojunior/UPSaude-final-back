package com.upsaude.service.api.support.agendamento;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.agendamento.AgendamentoRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.agendamento.AgendamentoMapper;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.service.api.financeiro.FinanceiroIntegrationService;
import com.upsaude.service.sistema.integracao.IntegracaoEventoGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendamentoCreator {

    private final AgendamentoRepository repository;
    private final AgendamentoMapper mapper;
    private final AgendamentoValidationService validationService;
    private final AgendamentoRelacionamentosHandler relacionamentosHandler;
    private final AgendamentoDomainService domainService;
    private final IntegracaoEventoGenerator eventoGenerator;
    private final FinanceiroIntegrationService financeiroIntegrationService;
    private final com.upsaude.service.sistema.notificacao.NotificacaoOrchestrator notificacaoOrchestrator;

    public Agendamento criar(AgendamentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Agendamento entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Agendamento saved = repository.save(Objects.requireNonNull(entity));
        eventoGenerator.gerarEventosParaAgendamento(saved);

        // Reserva automática no momento da aprovação/confirmação (modelo híbrido)
        if (saved.getStatus() == com.upsaude.enums.StatusAgendamentoEnum.CONFIRMADO) {
            financeiroIntegrationService.reservarOrcamento(saved.getId());
            // Notificar confirmação de agendamento
            try {
                notificacaoOrchestrator.notificarAgendamentoConfirmado(saved);
                notificacaoOrchestrator.agendarLembretesAgendamento(saved);
            } catch (Exception e) {
                log.warn("Erro ao enviar notificação de agendamento confirmado. Agendamento ID: {}", saved.getId(), e);
            }
        } else {
            // Agendar lembretes mesmo se não confirmado ainda
            try {
                notificacaoOrchestrator.agendarLembretesAgendamento(saved);
            } catch (Exception e) {
                log.warn("Erro ao agendar lembretes de agendamento. Agendamento ID: {}", saved.getId(), e);
            }
        }

        log.info("Agendamento criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

