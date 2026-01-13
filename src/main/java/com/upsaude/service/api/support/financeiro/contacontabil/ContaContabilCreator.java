package com.upsaude.service.api.support.financeiro.contacontabil;

import com.upsaude.api.request.financeiro.ContaContabilRequest;
import com.upsaude.entity.financeiro.ContaContabil;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.financeiro.ContaContabilMapper;
import com.upsaude.repository.financeiro.ContaContabilRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContaContabilCreator {

    private final ContaContabilRepository repository;
    private final ContaContabilMapper mapper;
    private final ContaContabilValidationService validationService;
    private final ContaContabilRelacionamentosHandler relacionamentosHandler;
    private final ContaContabilDomainService domainService;

    public ContaContabil criar(ContaContabilRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarUnicidadeParaCriacao(request, tenantId);

        ContaContabil entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        ContaContabil saved = repository.save(Objects.requireNonNull(entity));
        log.info("Conta contábil criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

