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
public class EquipamentosCreator {

    private final EquipamentosRepository repository;
    private final EquipamentosMapper mapper;
    private final EquipamentosValidationService validationService;
    private final EquipamentosRelacionamentosHandler relacionamentosHandler;

    public Equipamentos criar(EquipamentosRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        Equipamentos entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        Equipamentos saved = repository.save(Objects.requireNonNull(entity));
        log.info("Equipamento criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
