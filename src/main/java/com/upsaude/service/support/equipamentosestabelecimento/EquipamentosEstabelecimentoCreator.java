package com.upsaude.service.support.equipamentosestabelecimento;

import com.upsaude.api.request.EquipamentosEstabelecimentoRequest;
import com.upsaude.entity.EquipamentosEstabelecimento;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.EquipamentosEstabelecimentoMapper;
import com.upsaude.repository.EquipamentosEstabelecimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipamentosEstabelecimentoCreator {

    private final EquipamentosEstabelecimentoRepository repository;
    private final EquipamentosEstabelecimentoMapper mapper;
    private final EquipamentosEstabelecimentoValidationService validationService;
    private final EquipamentosEstabelecimentoRelacionamentosHandler relacionamentosHandler;
    private final EquipamentosEstabelecimentoDomainService domainService;

    public EquipamentosEstabelecimento criar(EquipamentosEstabelecimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        EquipamentosEstabelecimento entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        EquipamentosEstabelecimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Equipamento do estabelecimento criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
