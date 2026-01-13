package com.upsaude.service.api.support.agendamento;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.agendamento.AgendamentoRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.agendamento.AgendamentoMapper;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.service.sistema.integracao.IntegracaoEventoGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendamentoUpdater {

    private final AgendamentoRepository repository;
    private final AgendamentoTenantEnforcer tenantEnforcer;
    private final AgendamentoValidationService validationService;
    private final AgendamentoRelacionamentosHandler relacionamentosHandler;
    private final AgendamentoDomainService domainService;
    private final AgendamentoMapper mapper;
    private final IntegracaoEventoGenerator eventoGenerator;

    public Agendamento atualizar(UUID id, AgendamentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        Agendamento entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (request.getStatus() != null && !request.getStatus().equals(entity.getStatus())) {
            domainService.validarTransicaoStatus(entity, request.getStatus());
        }

        mapper.updateFromRequest(request, entity);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Agendamento saved = repository.save(Objects.requireNonNull(entity));
        eventoGenerator.gerarEventosParaAgendamento(saved);
        log.info("Agendamento atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

