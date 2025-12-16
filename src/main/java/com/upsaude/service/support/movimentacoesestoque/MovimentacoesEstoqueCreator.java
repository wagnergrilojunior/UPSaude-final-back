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
public class MovimentacoesEstoqueCreator {

    private final MovimentacoesEstoqueRepository repository;
    private final MovimentacoesEstoqueMapper mapper;
    private final MovimentacoesEstoqueValidationService validationService;
    private final MovimentacoesEstoqueRelacionamentosHandler relacionamentosHandler;
    private final MovimentacoesEstoqueDomainService domainService;

    public MovimentacoesEstoque criar(MovimentacoesEstoqueRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        MovimentacoesEstoque entity = mapper.fromRequest(request);
        entity.setActive(true);

        domainService.aplicarDefaults(entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        MovimentacoesEstoque saved = repository.save(Objects.requireNonNull(entity));
        log.info("Movimentação de estoque criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
