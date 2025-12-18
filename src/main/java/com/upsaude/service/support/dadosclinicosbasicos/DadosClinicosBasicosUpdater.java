package com.upsaude.service.support.dadosclinicosbasicos;

import com.upsaude.api.request.paciente.DadosClinicosBasicosRequest;
import com.upsaude.entity.paciente.DadosClinicosBasicos;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.DadosClinicosBasicosMapper;
import com.upsaude.repository.paciente.DadosClinicosBasicosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DadosClinicosBasicosUpdater {

    private final DadosClinicosBasicosRepository repository;
    private final DadosClinicosBasicosMapper mapper;
    private final DadosClinicosBasicosTenantEnforcer tenantEnforcer;
    private final DadosClinicosBasicosValidationService validationService;
    private final DadosClinicosBasicosRelacionamentosHandler relacionamentosHandler;

    public DadosClinicosBasicos atualizar(UUID id, DadosClinicosBasicosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        DadosClinicosBasicos entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (request.getPaciente() != null && (entity.getPaciente() == null || !request.getPaciente().equals(entity.getPaciente().getId()))) {
            validationService.validarDuplicidadeParaAtualizacao(id, request.getPaciente(), tenantId);
            relacionamentosHandler.processarRelacionamentos(entity, request, tenantId);
        }

        mapper.updateFromRequest(request, entity);
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para atualizar dados clínicos básicos"));

        DadosClinicosBasicos updated = repository.save(Objects.requireNonNull(entity));
        log.info("Dados clínicos básicos atualizados. ID: {}, tenant: {}", updated.getId(), tenantId);
        return updated;
    }
}

