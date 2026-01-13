package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.PagamentoPagarRequest;
import com.upsaude.api.response.financeiro.PagamentoPagarResponse;
import com.upsaude.entity.financeiro.ContaFinanceira;
import com.upsaude.entity.financeiro.PagamentoPagar;
import com.upsaude.entity.financeiro.TituloPagar;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.financeiro.PagamentoPagarMapper;
import com.upsaude.repository.financeiro.ContaFinanceiraRepository;
import com.upsaude.repository.financeiro.PagamentoPagarRepository;
import com.upsaude.repository.financeiro.TituloPagarRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.PagamentoPagarService;
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
public class PagamentoPagarServiceImpl implements PagamentoPagarService {

    private final PagamentoPagarRepository repository;
    private final TituloPagarRepository tituloPagarRepository;
    private final ContaFinanceiraRepository contaFinanceiraRepository;
    private final PagamentoPagarMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public PagamentoPagarResponse criar(PagamentoPagarRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        TituloPagar titulo = tituloPagarRepository.findByIdAndTenant(request.getTituloPagar(), tenantId)
                .orElseThrow(() -> new BadRequestException("Título a pagar não encontrado com ID: " + request.getTituloPagar()));

        BigDecimal aberto = titulo.getValorAberto() != null ? titulo.getValorAberto() : BigDecimal.ZERO;
        if (request.getValorPago().compareTo(aberto) > 0) {
            throw new BadRequestException("Valor pago não pode ser maior que o valor em aberto do título");
        }

        ContaFinanceira conta = contaFinanceiraRepository.findByIdAndTenant(request.getContaFinanceira(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conta financeira não encontrada com ID: " + request.getContaFinanceira()));

        PagamentoPagar entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        entity.setTituloPagar(titulo);
        entity.setContaFinanceira(conta);
        entity.setIdempotencyKey(UUID.randomUUID().toString());

        PagamentoPagar saved = repository.save(entity);

        titulo.setValorAberto(aberto.subtract(request.getValorPago()));
        if (titulo.getValorAberto().compareTo(BigDecimal.ZERO) <= 0) {
            titulo.setValorAberto(BigDecimal.ZERO);
            titulo.setStatus("PAGO");
        } else if (titulo.getValorAberto().compareTo(titulo.getValorOriginal()) < 0) {
            titulo.setStatus("PARCIAL");
        } else {
            titulo.setStatus("ABERTO");
        }
        tituloPagarRepository.save(titulo);

        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PagamentoPagarResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        PagamentoPagar entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Pagamento a pagar não encontrado com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PagamentoPagarResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public PagamentoPagarResponse atualizar(UUID id, PagamentoPagarRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        PagamentoPagar entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Pagamento a pagar não encontrado com ID: " + id));
        mapper.updateFromRequest(request, entity);
        PagamentoPagar saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            PagamentoPagar entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Pagamento a pagar não encontrado com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir pagamento a pagar", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            PagamentoPagar entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Pagamento a pagar não encontrado com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Pagamento a pagar já está inativo");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar pagamento a pagar", e);
        }
    }
}

