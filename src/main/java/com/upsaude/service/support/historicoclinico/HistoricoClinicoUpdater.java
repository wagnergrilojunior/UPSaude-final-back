package com.upsaude.service.support.historicoclinico;

import com.upsaude.api.request.prontuario.HistoricoClinicoRequest;
import com.upsaude.entity.prontuario.HistoricoClinico;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.HistoricoClinicoMapper;
import com.upsaude.repository.prontuario.HistoricoClinicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoricoClinicoUpdater {

    private final HistoricoClinicoRepository repository;
    private final HistoricoClinicoTenantEnforcer tenantEnforcer;
    private final HistoricoClinicoValidationService validationService;
    private final HistoricoClinicoRelacionamentosHandler relacionamentosHandler;
    private final HistoricoClinicoDomainService domainService;
    private final HistoricoClinicoMapper mapper;

    public HistoricoClinico atualizar(UUID id, HistoricoClinicoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        HistoricoClinico entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        HistoricoClinico saved = repository.save(Objects.requireNonNull(entity));
        log.info("Registro atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
