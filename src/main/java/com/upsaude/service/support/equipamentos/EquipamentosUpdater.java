package com.upsaude.service.support.equipamentos;

import com.upsaude.api.request.equipamento.EquipamentosRequest;
import com.upsaude.entity.equipamento.Equipamentos;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.EquipamentosMapper;
import com.upsaude.repository.estabelecimento.equipamento.EquipamentosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipamentosUpdater {

    private final EquipamentosRepository repository;
    private final EquipamentosMapper mapper;
    private final EquipamentosTenantEnforcer tenantEnforcer;
    private final EquipamentosValidationService validationService;
    private final EquipamentosRelacionamentosHandler relacionamentosHandler;
    private final EquipamentosDomainService domainService;

    public Equipamentos atualizar(UUID id, EquipamentosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Equipamentos entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Equipamentos saved = repository.save(Objects.requireNonNull(entity));
        log.info("Equipamento atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
