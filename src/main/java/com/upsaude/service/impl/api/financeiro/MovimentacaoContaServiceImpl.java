package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.MovimentacaoContaRequest;
import com.upsaude.api.response.financeiro.MovimentacaoContaResponse;
import com.upsaude.entity.financeiro.ContaFinanceira;
import com.upsaude.entity.financeiro.MovimentacaoConta;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.financeiro.MovimentacaoContaMapper;
import com.upsaude.repository.financeiro.ContaFinanceiraRepository;
import com.upsaude.repository.financeiro.MovimentacaoContaRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.MovimentacaoContaService;
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
public class MovimentacaoContaServiceImpl implements MovimentacaoContaService {

    private final MovimentacaoContaRepository repository;
    private final ContaFinanceiraRepository contaFinanceiraRepository;
    private final MovimentacaoContaMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public MovimentacaoContaResponse criar(MovimentacaoContaRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        ContaFinanceira conta = contaFinanceiraRepository.findByIdAndTenant(request.getContaFinanceira(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conta financeira não encontrada com ID: " + request.getContaFinanceira()));

        MovimentacaoConta entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        entity.setContaFinanceira(conta);
        entity.setIdempotencyKey(UUID.randomUUID().toString());

        MovimentacaoConta saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MovimentacaoContaResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        MovimentacaoConta entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Movimentação não encontrada com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimentacaoContaResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public MovimentacaoContaResponse atualizar(UUID id, MovimentacaoContaRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        MovimentacaoConta entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Movimentação não encontrada com ID: " + id));
        mapper.updateFromRequest(request, entity);
        MovimentacaoConta saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            MovimentacaoConta entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Movimentação não encontrada com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir movimentação", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            MovimentacaoConta entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Movimentação não encontrada com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Movimentação já está inativa");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar movimentação", e);
        }
    }
}

