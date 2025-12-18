package com.upsaude.service.support.equipamentosestabelecimento;

import com.upsaude.api.request.estabelecimento.EquipamentosEstabelecimentoRequest;
import com.upsaude.entity.estabelecimento.EquipamentosEstabelecimento;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.EquipamentosEstabelecimentoMapper;
import com.upsaude.repository.estabelecimento.equipamento.EquipamentosEstabelecimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipamentosEstabelecimentoUpdater {

    private final EquipamentosEstabelecimentoRepository repository;
    private final EquipamentosEstabelecimentoMapper mapper;
    private final EquipamentosEstabelecimentoTenantEnforcer tenantEnforcer;
    private final EquipamentosEstabelecimentoValidationService validationService;
    private final EquipamentosEstabelecimentoRelacionamentosHandler relacionamentosHandler;
    private final EquipamentosEstabelecimentoDomainService domainService;

    public EquipamentosEstabelecimento atualizar(UUID id, EquipamentosEstabelecimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        EquipamentosEstabelecimento entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        EquipamentosEstabelecimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Equipamento do estabelecimento atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
