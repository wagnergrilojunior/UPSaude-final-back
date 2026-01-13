package com.upsaude.service.api.support.financeiro.lancamento;

import com.upsaude.api.request.financeiro.LancamentoFinanceiroRequest;
import com.upsaude.entity.financeiro.LancamentoFinanceiro;
import com.upsaude.entity.financeiro.LancamentoFinanceiroItem;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.financeiro.LancamentoFinanceiroMapper;
import com.upsaude.repository.financeiro.LancamentoFinanceiroItemRepository;
import com.upsaude.repository.financeiro.LancamentoFinanceiroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LancamentoFinanceiroCreator {

    private final LancamentoFinanceiroRepository repository;
    private final LancamentoFinanceiroItemRepository itemRepository;
    private final LancamentoFinanceiroMapper mapper;
    private final LancamentoFinanceiroRelacionamentosHandler relacionamentosHandler;
    private final LancamentoFinanceiroDomainService domainService;

    public LancamentoFinanceiro criar(LancamentoFinanceiroRequest request, UUID tenantId, Tenant tenant) {
        LancamentoFinanceiro entity = mapper.fromRequest(request);
        entity.setActive(true);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);
        domainService.aplicarDefaults(entity);

        LancamentoFinanceiro saved = repository.save(Objects.requireNonNull(entity));

        List<LancamentoFinanceiroItem> itens = relacionamentosHandler.criarItens(saved, request.getItens(), tenantId, tenant);
        if (itens != null && !itens.isEmpty()) {
            itemRepository.saveAll(itens);
            saved.getItens().clear();
            saved.getItens().addAll(itens);
        }

        log.info("Lançamento financeiro criado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

