package com.upsaude.service.api.support.financeiro.plano;

import com.upsaude.api.request.financeiro.PlanoContasRequest;
import com.upsaude.entity.financeiro.PlanoContas;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.financeiro.PlanoContasMapper;
import com.upsaude.repository.financeiro.PlanoContasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanoContasCreator {

    private final PlanoContasRepository repository;
    private final PlanoContasMapper mapper;
    private final PlanoContasValidationService validationService;
    private final PlanoContasRelacionamentosHandler relacionamentosHandler;
    private final PlanoContasDomainService domainService;

    public PlanoContas criar(PlanoContasRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarUnicidadeParaCriacao(request, tenantId);

        PlanoContas entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        PlanoContas saved = repository.save(Objects.requireNonNull(entity));
        log.info("Plano de contas criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

