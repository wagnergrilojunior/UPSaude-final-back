package com.upsaude.service.support.historicohabilitacaoprofissional;

import com.upsaude.api.request.profissional.HistoricoHabilitacaoProfissionalRequest;
import com.upsaude.entity.profissional.HistoricoHabilitacaoProfissional;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.HistoricoHabilitacaoProfissionalMapper;
import com.upsaude.repository.profissional.HistoricoHabilitacaoProfissionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoricoHabilitacaoProfissionalUpdater {

    private final HistoricoHabilitacaoProfissionalRepository repository;
    private final HistoricoHabilitacaoProfissionalTenantEnforcer tenantEnforcer;
    private final HistoricoHabilitacaoProfissionalValidationService validationService;
    private final HistoricoHabilitacaoProfissionalRelacionamentosHandler relacionamentosHandler;
    private final HistoricoHabilitacaoProfissionalDomainService domainService;
    private final HistoricoHabilitacaoProfissionalMapper mapper;

    public HistoricoHabilitacaoProfissional atualizar(UUID id, HistoricoHabilitacaoProfissionalRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        HistoricoHabilitacaoProfissional entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        HistoricoHabilitacaoProfissional saved = repository.save(Objects.requireNonNull(entity));
        log.info("Registro atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
