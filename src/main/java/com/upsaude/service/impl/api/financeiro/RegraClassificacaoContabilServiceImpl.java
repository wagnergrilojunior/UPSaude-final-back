package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.RegraClassificacaoContabilRequest;
import com.upsaude.api.response.financeiro.RegraClassificacaoContabilResponse;
import com.upsaude.entity.financeiro.ContaContabil;
import com.upsaude.entity.financeiro.RegraClassificacaoContabil;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.financeiro.RegraClassificacaoContabilMapper;
import com.upsaude.repository.financeiro.ContaContabilRepository;
import com.upsaude.repository.financeiro.RegraClassificacaoContabilRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.RegraClassificacaoContabilService;
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
public class RegraClassificacaoContabilServiceImpl implements RegraClassificacaoContabilService {

    private final RegraClassificacaoContabilRepository repository;
    private final ContaContabilRepository contaContabilRepository;
    private final RegraClassificacaoContabilMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public RegraClassificacaoContabilResponse criar(RegraClassificacaoContabilRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        RegraClassificacaoContabil entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);

        ContaContabil conta = contaContabilRepository.findByIdAndTenant(request.getContaContabilDestino(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conta contábil destino não encontrada com ID: " + request.getContaContabilDestino()));
        entity.setContaContabilDestino(conta);

        RegraClassificacaoContabil saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public RegraClassificacaoContabilResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        RegraClassificacaoContabil entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Regra de classificação contábil não encontrada com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RegraClassificacaoContabilResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public RegraClassificacaoContabilResponse atualizar(UUID id, RegraClassificacaoContabilRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        RegraClassificacaoContabil entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Regra de classificação contábil não encontrada com ID: " + id));

        mapper.updateFromRequest(request, entity);
        ContaContabil conta = contaContabilRepository.findByIdAndTenant(request.getContaContabilDestino(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conta contábil destino não encontrada com ID: " + request.getContaContabilDestino()));
        entity.setContaContabilDestino(conta);

        RegraClassificacaoContabil saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            RegraClassificacaoContabil entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Regra de classificação contábil não encontrada com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir regra de classificação contábil", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            RegraClassificacaoContabil entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Regra de classificação contábil não encontrada com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Regra de classificação contábil já está inativa");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar regra de classificação contábil", e);
        }
    }
}

