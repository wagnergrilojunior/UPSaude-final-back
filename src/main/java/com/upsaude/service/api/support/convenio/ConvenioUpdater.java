package com.upsaude.service.api.support.convenio;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.convenio.ConvenioRequest;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.convenio.ConvenioMapper;
import com.upsaude.repository.convenio.ConvenioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvenioUpdater {

    private final ConvenioRepository repository;
    private final ConvenioMapper mapper;
    private final ConvenioTenantEnforcer tenantEnforcer;
    private final ConvenioValidationService validationService;
    private final ConvenioRelacionamentosHandler relacionamentosHandler;

    public Convenio atualizar(UUID id, ConvenioRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarUnicidadeParaAtualizacao(id, request, tenantId);

        Convenio entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Convenio saved = repository.save(Objects.requireNonNull(entity));
        log.info("Convênio atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
