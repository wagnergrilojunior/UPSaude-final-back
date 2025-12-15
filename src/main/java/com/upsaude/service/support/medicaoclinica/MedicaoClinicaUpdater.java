package com.upsaude.service.support.medicaoclinica;

import com.upsaude.api.request.MedicaoClinicaRequest;
import com.upsaude.entity.MedicaoClinica;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.MedicaoClinicaMapper;
import com.upsaude.repository.MedicaoClinicaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicaoClinicaUpdater {

    private final MedicaoClinicaRepository repository;
    private final MedicaoClinicaTenantEnforcer tenantEnforcer;
    private final MedicaoClinicaValidationService validationService;
    private final MedicaoClinicaRelacionamentosHandler relacionamentosHandler;
    private final MedicaoClinicaDomainService domainService;
    private final MedicaoClinicaMapper mapper;

    public MedicaoClinica atualizar(UUID id, MedicaoClinicaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        MedicaoClinica entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        MedicaoClinica saved = repository.save(Objects.requireNonNull(entity));
        log.info("Medição clínica atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
