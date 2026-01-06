package com.upsaude.service.api.support.dadossociodemograficos;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.paciente.DadosSociodemograficosRequest;
import com.upsaude.entity.paciente.DadosSociodemograficos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.paciente.DadosSociodemograficosMapper;
import com.upsaude.repository.paciente.DadosSociodemograficosRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DadosSociodemograficosUpdater {

    private final DadosSociodemograficosRepository repository;
    private final DadosSociodemograficosMapper mapper;
    private final DadosSociodemograficosTenantEnforcer tenantEnforcer;
    private final DadosSociodemograficosValidationService validationService;
    private final DadosSociodemograficosRelacionamentosHandler relacionamentosHandler;

    public DadosSociodemograficos atualizar(UUID id, DadosSociodemograficosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        DadosSociodemograficos entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (request.getPaciente() != null && (entity.getPaciente() == null || !request.getPaciente().equals(entity.getPaciente().getId()))) {
            validationService.validarDuplicidadeParaAtualizacao(id, request.getPaciente(), tenantId);
            relacionamentosHandler.processarRelacionamentos(entity, request, tenantId);
        }

        mapper.updateFromRequest(request, entity);
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para atualizar dados sociodemográficos"));

        DadosSociodemograficos updated = repository.save(Objects.requireNonNull(entity));
        log.info("Dados sociodemográficos atualizados. ID: {}, tenant: {}", updated.getId(), tenantId);
        return updated;
    }
}
