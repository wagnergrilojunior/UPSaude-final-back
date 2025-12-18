package com.upsaude.service.support.dispensacoesmedicamentos;

import com.upsaude.api.request.clinica.medicacao.DispensacoesMedicamentosRequest;
import com.upsaude.entity.clinica.medicacao.DispensacoesMedicamentos;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.clinica.medicacao.DispensacoesMedicamentosMapper;
import com.upsaude.repository.clinica.medicacao.DispensacoesMedicamentosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispensacoesMedicamentosUpdater {

    private final DispensacoesMedicamentosRepository repository;
    private final DispensacoesMedicamentosMapper mapper;
    private final DispensacoesMedicamentosTenantEnforcer tenantEnforcer;
    private final DispensacoesMedicamentosValidationService validationService;
    private final DispensacoesMedicamentosRelacionamentosHandler relacionamentosHandler;
    private final DispensacoesMedicamentosDomainService domainService;

    public DispensacoesMedicamentos atualizar(UUID id, DispensacoesMedicamentosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        DispensacoesMedicamentos entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        DispensacoesMedicamentos saved = repository.save(Objects.requireNonNull(entity));
        log.info("Dispensação de medicamento atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
