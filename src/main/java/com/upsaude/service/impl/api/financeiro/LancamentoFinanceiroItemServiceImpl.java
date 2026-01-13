package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.LancamentoFinanceiroItemRequest;
import com.upsaude.api.response.financeiro.LancamentoFinanceiroItemResponse;
import com.upsaude.entity.financeiro.CentroCusto;
import com.upsaude.entity.financeiro.ContaContabil;
import com.upsaude.entity.financeiro.LancamentoFinanceiro;
import com.upsaude.entity.financeiro.LancamentoFinanceiroItem;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.mapper.financeiro.LancamentoFinanceiroItemMapper;
import com.upsaude.repository.financeiro.CentroCustoRepository;
import com.upsaude.repository.financeiro.ContaContabilRepository;
import com.upsaude.repository.financeiro.LancamentoFinanceiroItemRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.LancamentoFinanceiroItemService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.financeiro.lancamento.LancamentoFinanceiroTenantEnforcer;
import com.upsaude.service.api.support.financeiro.lancamentoitem.LancamentoFinanceiroItemTenantEnforcer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LancamentoFinanceiroItemServiceImpl implements LancamentoFinanceiroItemService {

    private final LancamentoFinanceiroItemRepository repository;
    private final ContaContabilRepository contaContabilRepository;
    private final CentroCustoRepository centroCustoRepository;
    private final LancamentoFinanceiroItemMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    private final LancamentoFinanceiroItemTenantEnforcer tenantEnforcer;
    private final LancamentoFinanceiroTenantEnforcer lancamentoTenantEnforcer;

    @Override
    @Transactional
    public LancamentoFinanceiroItemResponse criar(UUID lancamentoId, LancamentoFinanceiroItemRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        LancamentoFinanceiro lancamento = lancamentoTenantEnforcer.validarAcesso(lancamentoId, tenantId);
        if (Boolean.TRUE.equals(lancamento.getTravado())) {
            throw new BadRequestException("Lançamento financeiro está travado e não pode receber novos itens");
        }

        LancamentoFinanceiroItem entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        entity.setLancamento(lancamento);

        ContaContabil conta = contaContabilRepository.findByIdAndTenant(request.getContaContabil(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conta contábil não encontrada com ID: " + request.getContaContabil()));
        entity.setContaContabil(conta);

        if (request.getCentroCusto() != null) {
            CentroCusto cc = centroCustoRepository.findByIdAndTenant(request.getCentroCusto(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Centro de custo não encontrado com ID: " + request.getCentroCusto()));
            entity.setCentroCusto(cc);
        }

        LancamentoFinanceiroItem saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public LancamentoFinanceiroItemResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        return mapper.toResponse(tenantEnforcer.validarAcessoCompleto(id, tenantId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LancamentoFinanceiroItemResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public LancamentoFinanceiroItemResponse atualizar(UUID id, LancamentoFinanceiroItemRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();

        LancamentoFinanceiroItem entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (entity.getLancamento() != null && Boolean.TRUE.equals(entity.getLancamento().getTravado())) {
            throw new BadRequestException("Lançamento financeiro está travado e não pode ser alterado");
        }

        mapper.updateFromRequest(request, entity);

        ContaContabil conta = contaContabilRepository.findByIdAndTenant(request.getContaContabil(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conta contábil não encontrada com ID: " + request.getContaContabil()));
        entity.setContaContabil(conta);

        if (request.getCentroCusto() != null) {
            CentroCusto cc = centroCustoRepository.findByIdAndTenant(request.getCentroCusto(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Centro de custo não encontrado com ID: " + request.getCentroCusto()));
            entity.setCentroCusto(cc);
        } else {
            entity.setCentroCusto(null);
        }

        LancamentoFinanceiroItem saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            LancamentoFinanceiroItem entity = tenantEnforcer.validarAcesso(id, tenantId);
            if (entity.getLancamento() != null && Boolean.TRUE.equals(entity.getLancamento().getTravado())) {
                throw new BadRequestException("Lançamento financeiro está travado e não pode ser alterado");
            }
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir item de lançamento", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            LancamentoFinanceiroItem entity = tenantEnforcer.validarAcesso(id, tenantId);
            if (entity.getLancamento() != null && Boolean.TRUE.equals(entity.getLancamento().getTravado())) {
                throw new BadRequestException("Lançamento financeiro está travado e não pode ser alterado");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar item de lançamento", e);
        }
    }
}

