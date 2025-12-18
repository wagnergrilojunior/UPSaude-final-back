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
public class HistoricoClinicoCreator {

    private final HistoricoClinicoRepository repository;
    private final HistoricoClinicoMapper mapper;
    private final HistoricoClinicoValidationService validationService;
    private final HistoricoClinicoRelacionamentosHandler relacionamentosHandler;
    private final HistoricoClinicoDomainService domainService;

    public HistoricoClinico criar(HistoricoClinicoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        HistoricoClinico entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        HistoricoClinico saved = repository.save(Objects.requireNonNull(entity));
        log.info("Registro criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
