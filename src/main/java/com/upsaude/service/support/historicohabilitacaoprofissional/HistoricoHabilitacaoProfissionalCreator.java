package com.upsaude.service.support.historicohabilitacaoprofissional;

import com.upsaude.api.request.HistoricoHabilitacaoProfissionalRequest;
import com.upsaude.entity.HistoricoHabilitacaoProfissional;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.HistoricoHabilitacaoProfissionalMapper;
import com.upsaude.repository.HistoricoHabilitacaoProfissionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoricoHabilitacaoProfissionalCreator {

    private final HistoricoHabilitacaoProfissionalRepository repository;
    private final HistoricoHabilitacaoProfissionalMapper mapper;
    private final HistoricoHabilitacaoProfissionalValidationService validationService;
    private final HistoricoHabilitacaoProfissionalRelacionamentosHandler relacionamentosHandler;
    private final HistoricoHabilitacaoProfissionalDomainService domainService;

    public HistoricoHabilitacaoProfissional criar(HistoricoHabilitacaoProfissionalRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        HistoricoHabilitacaoProfissional entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        HistoricoHabilitacaoProfissional saved = repository.save(Objects.requireNonNull(entity));
        log.info("Registro criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
