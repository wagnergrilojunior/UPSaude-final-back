package com.upsaude.service.support.movimentacoesestoque;

import com.upsaude.api.request.MovimentacoesEstoqueRequest;
import com.upsaude.entity.MovimentacoesEstoque;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.MovimentacoesEstoqueMapper;
import com.upsaude.repository.MovimentacoesEstoqueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovimentacoesEstoqueUpdater {

    private final MovimentacoesEstoqueRepository repository;
    private final MovimentacoesEstoqueTenantEnforcer tenantEnforcer;
    private final MovimentacoesEstoqueValidationService validationService;
    private final MovimentacoesEstoqueRelacionamentosHandler relacionamentosHandler;
    private final MovimentacoesEstoqueDomainService domainService;
    private final MovimentacoesEstoqueMapper mapper;

    public MovimentacoesEstoque atualizar(UUID id, MovimentacoesEstoqueRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        MovimentacoesEstoque entity = tenantEnforcer.validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        MovimentacoesEstoque saved = repository.save(Objects.requireNonNull(entity));
        log.info("Movimentação de estoque atualizada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
