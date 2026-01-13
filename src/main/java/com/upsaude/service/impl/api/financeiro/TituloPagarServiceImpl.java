package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.TituloPagarRequest;
import com.upsaude.api.response.financeiro.TituloPagarResponse;
import com.upsaude.entity.financeiro.CentroCusto;
import com.upsaude.entity.financeiro.ContaContabil;
import com.upsaude.entity.financeiro.ParteFinanceira;
import com.upsaude.entity.financeiro.RecorrenciaFinanceira;
import com.upsaude.entity.financeiro.TituloPagar;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.financeiro.TituloPagarMapper;
import com.upsaude.repository.financeiro.CentroCustoRepository;
import com.upsaude.repository.financeiro.ContaContabilRepository;
import com.upsaude.repository.financeiro.ParteFinanceiraRepository;
import com.upsaude.repository.financeiro.RecorrenciaFinanceiraRepository;
import com.upsaude.repository.financeiro.TituloPagarRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.TituloPagarService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
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
public class TituloPagarServiceImpl implements TituloPagarService {

    private final TituloPagarRepository repository;
    private final ParteFinanceiraRepository parteRepository;
    private final ContaContabilRepository contaContabilRepository;
    private final CentroCustoRepository centroCustoRepository;
    private final RecorrenciaFinanceiraRepository recorrenciaRepository;
    private final TituloPagarMapper mapper;

    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public TituloPagarResponse criar(TituloPagarRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        TituloPagar entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        entity.setIdempotencyKey(UUID.randomUUID().toString());

        ParteFinanceira fornecedor = parteRepository.findByIdAndTenant(request.getFornecedor(), tenantId)
                .orElseThrow(() -> new BadRequestException("Fornecedor não encontrado com ID: " + request.getFornecedor()));
        entity.setFornecedor(fornecedor);

        ContaContabil conta = contaContabilRepository.findByIdAndTenant(request.getContaContabilDespesa(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conta contábil não encontrada com ID: " + request.getContaContabilDespesa()));
        entity.setContaContabilDespesa(conta);

        CentroCusto cc = centroCustoRepository.findByIdAndTenant(request.getCentroCusto(), tenantId)
                .orElseThrow(() -> new BadRequestException("Centro de custo não encontrado com ID: " + request.getCentroCusto()));
        entity.setCentroCusto(cc);

        if (request.getRecorrenciaFinanceira() != null) {
            RecorrenciaFinanceira rec = recorrenciaRepository.findByIdAndTenant(request.getRecorrenciaFinanceira(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Recorrência financeira não encontrada com ID: " + request.getRecorrenciaFinanceira()));
            entity.setRecorrenciaFinanceira(rec);
        }

        TituloPagar saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TituloPagarResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        TituloPagar entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Título a pagar não encontrado com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TituloPagarResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public TituloPagarResponse atualizar(UUID id, TituloPagarRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        TituloPagar entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Título a pagar não encontrado com ID: " + id));

        mapper.updateFromRequest(request, entity);

        ParteFinanceira fornecedor = parteRepository.findByIdAndTenant(request.getFornecedor(), tenantId)
                .orElseThrow(() -> new BadRequestException("Fornecedor não encontrado com ID: " + request.getFornecedor()));
        entity.setFornecedor(fornecedor);

        ContaContabil conta = contaContabilRepository.findByIdAndTenant(request.getContaContabilDespesa(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conta contábil não encontrada com ID: " + request.getContaContabilDespesa()));
        entity.setContaContabilDespesa(conta);

        CentroCusto cc = centroCustoRepository.findByIdAndTenant(request.getCentroCusto(), tenantId)
                .orElseThrow(() -> new BadRequestException("Centro de custo não encontrado com ID: " + request.getCentroCusto()));
        entity.setCentroCusto(cc);

        if (request.getRecorrenciaFinanceira() != null) {
            RecorrenciaFinanceira rec = recorrenciaRepository.findByIdAndTenant(request.getRecorrenciaFinanceira(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Recorrência financeira não encontrada com ID: " + request.getRecorrenciaFinanceira()));
            entity.setRecorrenciaFinanceira(rec);
        } else {
            entity.setRecorrenciaFinanceira(null);
        }

        TituloPagar saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            TituloPagar entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Título a pagar não encontrado com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir título a pagar", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            TituloPagar entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Título a pagar não encontrado com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Título a pagar já está inativo");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar título a pagar", e);
        }
    }
}

