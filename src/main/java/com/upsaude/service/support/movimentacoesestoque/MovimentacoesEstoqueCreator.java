package com.upsaude.service.support.movimentacoesestoque;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.estabelecimento.estoque.MovimentacoesEstoqueRequest;
import com.upsaude.entity.estabelecimento.estoque.MovimentacoesEstoque;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.estabelecimento.estoque.MovimentacoesEstoqueMapper;
import com.upsaude.repository.estabelecimento.estoque.MovimentacoesEstoqueRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovimentacoesEstoqueCreator {

    private final MovimentacoesEstoqueRepository repository;
    private final MovimentacoesEstoqueMapper mapper;
    private final MovimentacoesEstoqueValidationService validationService;
    private final MovimentacoesEstoqueRelacionamentosHandler relacionamentosHandler;

    public MovimentacoesEstoque criar(MovimentacoesEstoqueRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        MovimentacoesEstoque entity = mapper.fromRequest(request);
        entity.setActive(true);

        relacionamentosHandler.resolver(entity, request, tenantId, tenant);

        MovimentacoesEstoque saved = repository.save(Objects.requireNonNull(entity));
        log.info("Movimentação de estoque criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}
