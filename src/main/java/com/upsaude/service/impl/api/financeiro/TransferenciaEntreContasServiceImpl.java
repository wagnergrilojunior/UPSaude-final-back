package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.TransferenciaEntreContasRequest;
import com.upsaude.api.response.financeiro.TransferenciaEntreContasResponse;
import com.upsaude.entity.financeiro.ContaFinanceira;
import com.upsaude.entity.financeiro.TransferenciaEntreContas;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.financeiro.TransferenciaEntreContasMapper;
import com.upsaude.repository.financeiro.ContaFinanceiraRepository;
import com.upsaude.repository.financeiro.TransferenciaEntreContasRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.TransferenciaEntreContasService;
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
public class TransferenciaEntreContasServiceImpl implements TransferenciaEntreContasService {

    private final TransferenciaEntreContasRepository repository;
    private final ContaFinanceiraRepository contaFinanceiraRepository;
    private final TransferenciaEntreContasMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public TransferenciaEntreContasResponse criar(TransferenciaEntreContasRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        ContaFinanceira origem = contaFinanceiraRepository.findByIdAndTenant(request.getContaOrigem(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conta origem não encontrada com ID: " + request.getContaOrigem()));
        ContaFinanceira destino = contaFinanceiraRepository.findByIdAndTenant(request.getContaDestino(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conta destino não encontrada com ID: " + request.getContaDestino()));

        TransferenciaEntreContas entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        entity.setContaOrigem(origem);
        entity.setContaDestino(destino);
        entity.setIdempotencyKey(UUID.randomUUID().toString());

        TransferenciaEntreContas saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TransferenciaEntreContasResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        TransferenciaEntreContas entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Transferência não encontrada com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransferenciaEntreContasResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public TransferenciaEntreContasResponse atualizar(UUID id, TransferenciaEntreContasRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        TransferenciaEntreContas entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Transferência não encontrada com ID: " + id));
        mapper.updateFromRequest(request, entity);
        TransferenciaEntreContas saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            TransferenciaEntreContas entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Transferência não encontrada com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir transferência", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            TransferenciaEntreContas entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Transferência não encontrada com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Transferência já está inativa");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar transferência", e);
        }
    }
}

