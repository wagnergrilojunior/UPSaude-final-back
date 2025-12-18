package com.upsaude.service.support.infraestruturaestabelecimento;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.estabelecimento.InfraestruturaEstabelecimentoRequest;
import com.upsaude.entity.estabelecimento.InfraestruturaEstabelecimento;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.estabelecimento.InfraestruturaEstabelecimentoMapper;
import com.upsaude.repository.estabelecimento.InfraestruturaEstabelecimentoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InfraestruturaEstabelecimentoCreator {

    private final InfraestruturaEstabelecimentoRepository repository;
    private final InfraestruturaEstabelecimentoMapper mapper;
    private final InfraestruturaEstabelecimentoValidationService validationService;
    private final InfraestruturaEstabelecimentoRelacionamentosHandler relacionamentosHandler;

    public InfraestruturaEstabelecimento criar(InfraestruturaEstabelecimentoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        InfraestruturaEstabelecimento entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        InfraestruturaEstabelecimento saved = repository.save(Objects.requireNonNull(entity));
        log.info("Infraestrutura do estabelecimento criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
