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
public class LancamentoFinanceiroUpdater {

    private final LancamentoFinanceiroRepository repository;
    private final LancamentoFinanceiroItemRepository itemRepository;
    private final LancamentoFinanceiroMapper mapper;
    private final LancamentoFinanceiroTenantEnforcer tenantEnforcer;
    private final LancamentoFinanceiroRelacionamentosHandler relacionamentosHandler;
    private final LancamentoFinanceiroDomainService domainService;

    public LancamentoFinanceiro atualizar(UUID id, LancamentoFinanceiroRequest request, UUID tenantId, Tenant tenant) {
        LancamentoFinanceiro entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarImutabilidade(entity);

        mapper.updateFromRequest(request, entity);
        relacionamentosHandler.resolver(entity, request, tenantId, tenant);
        domainService.aplicarDefaults(entity);

        LancamentoFinanceiro saved = repository.save(Objects.requireNonNull(entity));

        if (request.getItens() != null) {
            List<LancamentoFinanceiroItem> existentes = itemRepository.findByLancamento(saved.getId(), tenantId);
            if (existentes != null && !existentes.isEmpty()) {
                itemRepository.deleteAll(existentes);
            }
            List<LancamentoFinanceiroItem> novos = relacionamentosHandler.criarItens(saved, request.getItens(), tenantId, tenant);
            if (novos != null && !novos.isEmpty()) {
                itemRepository.saveAll(novos);
                saved.getItens().clear();
                saved.getItens().addAll(novos);
            } else {
                saved.getItens().clear();
            }
        }

        log.info("Lançamento financeiro atualizado com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
        return saved;
    }
}

