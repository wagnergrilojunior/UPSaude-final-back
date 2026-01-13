package com.upsaude.service.api.support.financeiro.centrocusto;

import com.upsaude.api.request.financeiro.CentroCustoRequest;
import com.upsaude.entity.financeiro.CentroCusto;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.financeiro.CentroCustoMapper;
import com.upsaude.repository.financeiro.CentroCustoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CentroCustoCreator {

    private final CentroCustoRepository repository;
    private final CentroCustoMapper mapper;
    private final CentroCustoValidationService validationService;
    private final CentroCustoRelacionamentosHandler relacionamentosHandler;
    private final CentroCustoDomainService domainService;

    public CentroCusto criar(CentroCustoRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarUnicidadeParaCriacao(request, tenantId);

        CentroCusto entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        CentroCusto saved = repository.save(Objects.requireNonNull(entity));
        log.info("Centro de custo criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

