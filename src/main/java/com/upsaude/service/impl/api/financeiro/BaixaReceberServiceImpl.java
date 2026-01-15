package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.BaixaReceberRequest;
import com.upsaude.api.response.financeiro.BaixaReceberResponse;
import com.upsaude.entity.financeiro.BaixaReceber;
import com.upsaude.entity.financeiro.ContaFinanceira;
import com.upsaude.entity.financeiro.TituloReceber;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.financeiro.BaixaReceberMapper;
import com.upsaude.repository.financeiro.BaixaReceberRepository;
import com.upsaude.repository.financeiro.ContaFinanceiraRepository;
import com.upsaude.repository.financeiro.TituloReceberRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.BaixaReceberService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaixaReceberServiceImpl implements BaixaReceberService {

    private final BaixaReceberRepository repository;
    private final TituloReceberRepository tituloReceberRepository;
    private final ContaFinanceiraRepository contaFinanceiraRepository;
    private final BaixaReceberMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public BaixaReceberResponse criar(BaixaReceberRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        TituloReceber titulo = tituloReceberRepository.findByIdAndTenant(request.getTituloReceber(), tenantId)
                .orElseThrow(() -> new BadRequestException("Título a receber não encontrado com ID: " + request.getTituloReceber()));

        BigDecimal aberto = titulo.getValorAberto() != null ? titulo.getValorAberto() : BigDecimal.ZERO;
        if (request.getValorPago().compareTo(aberto) > 0) {
            throw new BadRequestException("Valor pago não pode ser maior que o valor em aberto do título");
        }

        ContaFinanceira conta = contaFinanceiraRepository.findByIdAndTenant(request.getContaFinanceira(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conta financeira não encontrada com ID: " + request.getContaFinanceira()));

        BaixaReceber entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        entity.setTituloReceber(titulo);
        entity.setContaFinanceira(conta);
        entity.setIdempotencyKey(UUID.randomUUID().toString());

        BaixaReceber saved = repository.save(entity);

        titulo.setValorAberto(aberto.subtract(request.getValorPago()));
        if (titulo.getValorAberto().compareTo(BigDecimal.ZERO) <= 0) {
            titulo.setValorAberto(BigDecimal.ZERO);
            titulo.setStatus("PAGO");
        } else if (titulo.getValorAberto().compareTo(titulo.getValorOriginal()) < 0) {
            titulo.setStatus("PARCIAL");
        } else {
            titulo.setStatus("ABERTO");
        }
        tituloReceberRepository.save(titulo);

        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BaixaReceberResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        BaixaReceber entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Baixa a receber não encontrada com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BaixaReceberResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public BaixaReceberResponse atualizar(UUID id, BaixaReceberRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        BaixaReceber entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Baixa a receber não encontrada com ID: " + id));

        mapper.updateFromRequest(request, entity);
        BaixaReceber saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            BaixaReceber entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Baixa a receber não encontrada com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir baixa a receber", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            BaixaReceber entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Baixa a receber não encontrada com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Baixa a receber já está inativa");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar baixa a receber", e);
        }
    }
}

