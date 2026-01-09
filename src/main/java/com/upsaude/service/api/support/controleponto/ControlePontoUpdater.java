package com.upsaude.service.api.support.controleponto;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.profissional.equipe.ControlePontoRequest;
import com.upsaude.entity.profissional.equipe.ControlePonto;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.profissional.equipe.ControlePontoMapper;
import com.upsaude.repository.profissional.equipe.ControlePontoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ControlePontoUpdater {

    private final ControlePontoRepository repository;
    private final ControlePontoMapper mapper;
    private final ControlePontoTenantEnforcer tenantEnforcer;
    private final ControlePontoValidationService validationService;
    private final ControlePontoRelacionamentosHandler relacionamentosHandler;

    public ControlePonto atualizar(UUID id, ControlePontoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        ControlePonto entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        ControlePonto saved = repository.save(Objects.requireNonNull(entity));
        log.info("Controle de ponto atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
