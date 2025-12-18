package com.upsaude.service.support.equipamentosestabelecimento;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.estabelecimento.EquipamentosEstabelecimentoRequest;
import com.upsaude.entity.estabelecimento.EquipamentosEstabelecimento;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.estabelecimento.EquipamentosEstabelecimentoMapper;
import com.upsaude.repository.estabelecimento.equipamento.EquipamentosEstabelecimentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipamentosEstabelecimentoCreator {

    private final EquipamentosEstabelecimentoRepository repository;
    private final EquipamentosEstabelecimentoMapper mapper;
    private final EquipamentosEstabelecimentoValidationService validationService;
    private final EquipamentosEstabelecimentoRelacionamentosHandler relacionamentosHandler;

    public EquipamentosEstabelecimento criar(EquipamentosEstabelecimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request, tenantId);

        EquipamentosEstabelecimento entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        EquipamentosEstabelecimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Equipamento do estabelecimento criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
