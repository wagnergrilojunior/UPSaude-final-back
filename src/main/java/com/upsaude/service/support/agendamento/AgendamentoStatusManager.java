package com.upsaude.service.support.agendamento;

import com.upsaude.api.request.AgendamentoRequest;
import com.upsaude.entity.Agendamento;
import com.upsaude.enums.StatusAgendamentoEnum;
import com.upsaude.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendamentoStatusManager {

    private final AgendamentoTenantEnforcer tenantEnforcer;
    private final AgendamentoDomainService domainService;
    private final AgendamentoCreator creator;
    private final AgendamentoRepository repository;

    public Agendamento cancelar(UUID id, String motivoCancelamento, UUID tenantId) {
        Agendamento agendamento = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeCancelar(agendamento);

        agendamento.setStatus(StatusAgendamentoEnum.CANCELADO);
        agendamento.setDataCancelamento(OffsetDateTime.now());
        agendamento.setMotivoCancelamento(motivoCancelamento);
        agendamento.setDataUltimaAlteracao(OffsetDateTime.now());

        Agendamento salvo = repository.save(Objects.requireNonNull(agendamento));
        log.info("Agendamento cancelado. ID: {}, tenant: {}", id, tenantId);
        return salvo;
    }

    public Agendamento confirmar(UUID id, UUID tenantId) {
        Agendamento agendamento = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeConfirmar(agendamento);

        agendamento.setStatus(StatusAgendamentoEnum.CONFIRMADO);
        agendamento.setDataConfirmacao(OffsetDateTime.now());
        agendamento.setDataUltimaAlteracao(OffsetDateTime.now());

        Agendamento salvo = repository.save(Objects.requireNonNull(agendamento));
        log.info("Agendamento confirmado. ID: {}, tenant: {}", id, tenantId);
        return salvo;
    }

    public Agendamento reagendar(UUID id, AgendamentoRequest novoAgendamentoRequest, String motivoReagendamento, UUID tenantId) {
        Agendamento original = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeReagendar(original);

        original.setStatus(StatusAgendamentoEnum.REAGENDADO);
        original.setDataReagendamento(OffsetDateTime.now());
        original.setMotivoReagendamento(motivoReagendamento);
        original.setDataUltimaAlteracao(OffsetDateTime.now());
        repository.save(Objects.requireNonNull(original));

        novoAgendamentoRequest.setAgendamentoOriginal(id);
        novoAgendamentoRequest.setStatus(StatusAgendamentoEnum.AGENDADO);
        Agendamento novo = creator.criar(novoAgendamentoRequest, tenantId);

        log.info("Agendamento reagendado. Original: {}, Novo: {}, tenant: {}", id, novo.getId(), tenantId);
        return novo;
    }
}
