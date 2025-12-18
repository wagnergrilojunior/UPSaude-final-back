package com.upsaude.service.support.integracaogov;

import com.upsaude.api.request.integracao.IntegracaoGovRequest;
import com.upsaude.entity.integracao.IntegracaoGov;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.ConflictException;
import com.upsaude.mapper.IntegracaoGovMapper;
import com.upsaude.repository.sistema.integracao.IntegracaoGovRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegracaoGovCreator {

    private final IntegracaoGovRepository repository;
    private final IntegracaoGovMapper mapper;
    private final IntegracaoGovValidationService validationService;
    private final IntegracaoGovRelacionamentosHandler relacionamentosHandler;
    private final IntegracaoGovDomainService domainService;

    public IntegracaoGov criar(IntegracaoGovRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        repository.findByPacienteIdAndTenantId(request.getPaciente(), tenantId)
            .ifPresent(d -> {
                throw new ConflictException("Integração gov já existe para este paciente");
            });

        IntegracaoGov entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        IntegracaoGov saved = repository.save(Objects.requireNonNull(entity));
        log.info("Integração gov criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
