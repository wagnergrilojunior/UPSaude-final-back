package com.upsaude.service.api.support.historicoclinico;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.prontuario.HistoricoClinicoRequest;
import com.upsaude.entity.clinica.prontuario.HistoricoClinico;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.clinica.prontuario.HistoricoClinicoMapper;
import com.upsaude.repository.clinica.prontuario.HistoricoClinicoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoricoClinicoUpdater {

    private final HistoricoClinicoRepository repository;
    private final HistoricoClinicoMapper mapper;
    private final HistoricoClinicoTenantEnforcer tenantEnforcer;
    private final HistoricoClinicoValidationService validationService;
    private final HistoricoClinicoRelacionamentosHandler relacionamentosHandler;

    public HistoricoClinico atualizar(UUID id, HistoricoClinicoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        HistoricoClinico entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        HistoricoClinico saved = repository.save(Objects.requireNonNull(entity));
        log.info("Histórico clínico atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
