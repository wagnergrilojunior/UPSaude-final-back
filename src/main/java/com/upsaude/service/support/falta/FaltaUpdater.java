package com.upsaude.service.support.falta;

import com.upsaude.api.request.equipe.FaltaRequest;
import com.upsaude.entity.equipe.Falta;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.FaltaMapper;
import com.upsaude.repository.profissional.equipe.FaltaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaltaUpdater {

    private final FaltaRepository repository;
    private final FaltaMapper mapper;
    private final FaltaTenantEnforcer tenantEnforcer;
    private final FaltaValidationService validationService;
    private final FaltaRelacionamentosHandler relacionamentosHandler;

    public Falta atualizar(UUID id, FaltaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarConsistenciaDatas(request);

        Falta entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);

        Falta saved = repository.save(Objects.requireNonNull(entity));
        log.info("Falta atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

