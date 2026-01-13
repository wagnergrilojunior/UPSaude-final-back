package com.upsaude.service.api.support.financeiro.contafinanceira;

import com.upsaude.api.request.financeiro.ContaFinanceiraRequest;
import com.upsaude.entity.financeiro.ContaFinanceira;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.financeiro.ContaFinanceiraMapper;
import com.upsaude.repository.financeiro.ContaFinanceiraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContaFinanceiraCreator {

    private final ContaFinanceiraRepository repository;
    private final ContaFinanceiraMapper mapper;
    private final ContaFinanceiraValidationService validationService;
    private final ContaFinanceiraRelacionamentosHandler relacionamentosHandler;
    private final ContaFinanceiraDomainService domainService;

    public ContaFinanceira criar(ContaFinanceiraRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarUnicidadeParaCriacao(request, tenantId);

        ContaFinanceira entity = mapper.fromRequest(request);
        entity.setActive(true);
        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        ContaFinanceira saved = repository.save(Objects.requireNonNull(entity));
        log.info("Conta financeira criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

