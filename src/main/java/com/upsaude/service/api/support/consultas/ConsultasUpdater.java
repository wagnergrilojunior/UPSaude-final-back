package com.upsaude.service.api.support.consultas;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.atendimento.ConsultaRequest;
import com.upsaude.entity.clinica.atendimento.Consulta;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.clinica.atendimento.ConsultaMapper;
import com.upsaude.repository.clinica.atendimento.ConsultasRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultasUpdater {

    private final ConsultasRepository repository;
    private final ConsultaMapper mapper;
    private final ConsultasTenantEnforcer tenantEnforcer;
    private final ConsultasValidationService validationService;
    private final ConsultasRelacionamentosHandler relacionamentosHandler;
    private final ConsultasDomainService domainService;

    public Consulta atualizar(UUID id, ConsultaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Consulta entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Consulta saved = repository.save(Objects.requireNonNull(entity));
        log.info("Consulta atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
