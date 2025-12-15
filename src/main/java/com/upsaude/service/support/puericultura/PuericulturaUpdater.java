package com.upsaude.service.support.puericultura;

import com.upsaude.api.request.PuericulturaRequest;
import com.upsaude.entity.Puericultura;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.PuericulturaMapper;
import com.upsaude.repository.PuericulturaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PuericulturaUpdater {

    private final PuericulturaRepository repository;
    private final PuericulturaMapper mapper;
    private final PuericulturaTenantEnforcer tenantEnforcer;
    private final PuericulturaValidationService validationService;
    private final PuericulturaDomainService domainService;
    private final PuericulturaRelacionamentosHandler relacionamentosHandler;

    public Puericultura atualizar(UUID id, PuericulturaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Puericultura entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Puericultura saved = repository.save(Objects.requireNonNull(entity));
        log.info("Puericultura atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

