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
public class PuericulturaCreator {

    private final PuericulturaRepository repository;
    private final PuericulturaMapper mapper;
    private final PuericulturaValidationService validationService;
    private final PuericulturaDomainService domainService;
    private final PuericulturaRelacionamentosHandler relacionamentosHandler;

    public Puericultura criar(PuericulturaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Puericultura entity = mapper.fromRequest(request);
        entity.setActive(true);
        domainService.aplicarDefaults(entity);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Puericultura saved = repository.save(Objects.requireNonNull(entity));
        log.info("Puericultura criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

