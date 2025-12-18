package com.upsaude.service.support.agendamento;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.agendamento.AgendamentoRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.mapper.agendamento.AgendamentoMapper;
import com.upsaude.repository.agendamento.AgendamentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendamentoUpdater {

    private final AgendamentoValidationService validationService;
    private final AgendamentoTenantEnforcer tenantEnforcer;
    private final AgendamentoRelacionamentosHandler relacionamentosHandler;
    private final AgendamentoMapper mapper;
    private final AgendamentoRepository repository;

    public Agendamento atualizar(UUID id, AgendamentoRequest request, UUID tenantId) {
        validationService.validarObrigatorios(request);
        validationService.verificarConflitosHorario(request, repository, tenantId);

        Agendamento existente = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, existente);
        existente.setDataUltimaAlteracao(OffsetDateTime.now());

        relacionamentosHandler.processarRelacionamentos(existente, request, tenantId);

        Agendamento salvo = repository.save(Objects.requireNonNull(existente));
        log.info("Agendamento atualizado com sucesso. ID: {}, tenant: {}", salvo.getId(), tenantId);
        return salvo;
    }
}
