package com.upsaude.service.support.integracaogov;

import com.upsaude.api.request.IntegracaoGovRequest;
import com.upsaude.entity.IntegracaoGov;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.ConflictException;
import com.upsaude.mapper.IntegracaoGovMapper;
import com.upsaude.repository.IntegracaoGovRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegracaoGovUpdater {

    private final IntegracaoGovRepository repository;
    private final IntegracaoGovTenantEnforcer tenantEnforcer;
    private final IntegracaoGovValidationService validationService;
    private final IntegracaoGovRelacionamentosHandler relacionamentosHandler;
    private final IntegracaoGovDomainService domainService;
    private final IntegracaoGovMapper mapper;

    public IntegracaoGov atualizar(UUID id, IntegracaoGovRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        IntegracaoGov entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (request.getPaciente() != null && entity.getPaciente() != null && !request.getPaciente().equals(entity.getPaciente().getId())) {
            repository.findByPacienteIdAndTenantId(request.getPaciente(), tenantId)
                .ifPresent(d -> {
                    if (!d.getId().equals(id)) {
                        throw new ConflictException("Integração gov já existe para este paciente");
                    }
                });
        }

        mapper.updateFromRequest(request, entity);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        IntegracaoGov saved = repository.save(Objects.requireNonNull(entity));
        log.info("Integração gov atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
