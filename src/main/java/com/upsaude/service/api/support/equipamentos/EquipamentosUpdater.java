package com.upsaude.service.api.support.equipamentos;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.estabelecimento.equipamento.EquipamentosRequest;
import com.upsaude.entity.estabelecimento.equipamento.Equipamentos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.estabelecimento.equipamento.EquipamentosMapper;
import com.upsaude.repository.estabelecimento.equipamento.EquipamentosRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipamentosUpdater {

    private final EquipamentosRepository repository;
    private final EquipamentosMapper mapper;
    private final EquipamentosTenantEnforcer tenantEnforcer;
    private final EquipamentosValidationService validationService;
    private final EquipamentosRelacionamentosHandler relacionamentosHandler;

    public Equipamentos atualizar(UUID id, EquipamentosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Equipamentos entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Equipamentos saved = repository.save(Objects.requireNonNull(entity));
        log.info("Equipamento atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
