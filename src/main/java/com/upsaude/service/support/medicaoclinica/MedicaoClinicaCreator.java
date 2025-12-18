package com.upsaude.service.support.medicaoclinica;

import com.upsaude.api.request.medicao.MedicaoClinicaRequest;
import com.upsaude.entity.medicao.MedicaoClinica;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.MedicaoClinicaMapper;
import com.upsaude.repository.profissional.medicao.MedicaoClinicaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicaoClinicaCreator {

    private final MedicaoClinicaRepository repository;
    private final MedicaoClinicaMapper mapper;
    private final MedicaoClinicaValidationService validationService;
    private final MedicaoClinicaRelacionamentosHandler relacionamentosHandler;
    private final MedicaoClinicaDomainService domainService;

    public MedicaoClinica criar(MedicaoClinicaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        MedicaoClinica entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        MedicaoClinica saved = repository.save(Objects.requireNonNull(entity));
        log.info("Medição clínica criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
