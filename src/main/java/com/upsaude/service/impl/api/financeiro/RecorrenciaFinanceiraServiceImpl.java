package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.RecorrenciaFinanceiraRequest;
import com.upsaude.api.response.financeiro.RecorrenciaFinanceiraResponse;
import com.upsaude.entity.financeiro.RecorrenciaFinanceira;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.financeiro.RecorrenciaFinanceiraMapper;
import com.upsaude.repository.financeiro.RecorrenciaFinanceiraRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.RecorrenciaFinanceiraService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecorrenciaFinanceiraServiceImpl implements RecorrenciaFinanceiraService {

    private final RecorrenciaFinanceiraRepository repository;
    private final RecorrenciaFinanceiraMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public RecorrenciaFinanceiraResponse criar(RecorrenciaFinanceiraRequest request) {
        UUID tenantId = Objects.requireNonNull(tenantService.validarTenantAtual(), "tenantId");
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            tenant = tenantRepository.findById(tenantId)
                    .orElseThrow(() -> new BadRequestException("Tenant não encontrado para o usuário autenticado"));
        }

        RecorrenciaFinanceira entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        RecorrenciaFinanceira saved = repository.save(Objects.requireNonNull(entity, "entity"));
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public RecorrenciaFinanceiraResponse obterPorId(UUID id) {
        UUID tenantId = Objects.requireNonNull(tenantService.validarTenantAtual(), "tenantId");
        RecorrenciaFinanceira entity = Objects.requireNonNull(
                repository.findByIdAndTenant(id, tenantId)
                        .orElseThrow(() -> new NotFoundException("Recorrência financeira não encontrada com ID: " + id)),
                "entity"
        );
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecorrenciaFinanceiraResponse> listar(Pageable pageable) {
        UUID tenantId = Objects.requireNonNull(tenantService.validarTenantAtual(), "tenantId");
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public RecorrenciaFinanceiraResponse atualizar(UUID id, RecorrenciaFinanceiraRequest request) {
        UUID tenantId = Objects.requireNonNull(tenantService.validarTenantAtual(), "tenantId");
        RecorrenciaFinanceira entity = Objects.requireNonNull(
                repository.findByIdAndTenant(id, tenantId)
                        .orElseThrow(() -> new NotFoundException("Recorrência financeira não encontrada com ID: " + id)),
                "entity"
        );

        mapper.updateFromRequest(request, entity);
        RecorrenciaFinanceira saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = Objects.requireNonNull(tenantService.validarTenantAtual(), "tenantId");
        try {
            RecorrenciaFinanceira entity = Objects.requireNonNull(
                    repository.findByIdAndTenant(id, tenantId)
                            .orElseThrow(() -> new NotFoundException("Recorrência financeira não encontrada com ID: " + id)),
                    "entity"
            );
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir recorrência financeira", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = Objects.requireNonNull(tenantService.validarTenantAtual(), "tenantId");
        try {
            RecorrenciaFinanceira entity = Objects.requireNonNull(
                    repository.findByIdAndTenant(id, tenantId)
                            .orElseThrow(() -> new NotFoundException("Recorrência financeira não encontrada com ID: " + id)),
                    "entity"
            );
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Recorrência financeira já está inativa");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar recorrência financeira", e);
        }
    }
}

