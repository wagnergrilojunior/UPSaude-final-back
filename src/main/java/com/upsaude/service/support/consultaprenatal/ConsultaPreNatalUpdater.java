package com.upsaude.service.support.consultaprenatal;

import com.upsaude.api.request.ConsultaPreNatalRequest;
import com.upsaude.entity.ConsultaPreNatal;
import com.upsaude.entity.PreNatal;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.ConsultaPreNatalMapper;
import com.upsaude.repository.ConsultaPreNatalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaPreNatalUpdater {

    private final ConsultaPreNatalRepository repository;
    private final ConsultaPreNatalMapper mapper;
    private final ConsultaPreNatalTenantEnforcer tenantEnforcer;
    private final ConsultaPreNatalValidationService validationService;
    private final ConsultaPreNatalRelacionamentosHandler relacionamentosHandler;
    private final ConsultaPreNatalDomainService domainService;

    public ConsultaPreNatal atualizar(UUID id, ConsultaPreNatalRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        ConsultaPreNatal entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.processarRelacionamentos(request, entity, tenantId, tenant);

        ConsultaPreNatal saved = repository.save(Objects.requireNonNull(entity));

        PreNatal preNatal = saved.getPreNatal();
        domainService.recalcularNumeroConsultas(preNatal, tenantId);

        log.info("Consulta pré-natal atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

