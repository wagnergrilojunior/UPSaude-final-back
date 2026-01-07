package com.upsaude.service.api.support.agendamento;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.agendamento.AgendamentoRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.agendamento.AgendamentoMapper;
import com.upsaude.repository.agendamento.AgendamentoRepository;

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

    public Agendamento criar(AgendamentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Agendamento entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Agendamento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Agendamento criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

