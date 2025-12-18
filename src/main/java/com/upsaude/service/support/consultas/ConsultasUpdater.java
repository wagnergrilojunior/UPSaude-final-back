package com.upsaude.service.support.consultas;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.atendimento.ConsultasRequest;
import com.upsaude.entity.clinica.atendimento.Consultas;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.clinica.atendimento.ConsultasMapper;
import com.upsaude.repository.clinica.atendimento.ConsultasRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultasUpdater {

    private final ConsultasRepository repository;
    private final ConsultasMapper mapper;
    private final ConsultasTenantEnforcer tenantEnforcer;
    private final ConsultasValidationService validationService;
    private final ConsultasRelacionamentosHandler relacionamentosHandler;
    private final ConsultasDomainService domainService;

    public Consultas atualizar(UUID id, ConsultasRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Consultas entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Consultas saved = repository.save(Objects.requireNonNull(entity));
        log.info("Consulta atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
