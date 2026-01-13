package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.ParteFinanceiraRequest;
import com.upsaude.api.response.financeiro.ParteFinanceiraResponse;
import com.upsaude.entity.financeiro.ParteFinanceira;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.financeiro.ParteFinanceiraMapper;
import com.upsaude.repository.financeiro.ParteFinanceiraRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.ParteFinanceiraService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParteFinanceiraServiceImpl implements ParteFinanceiraService {

    private final ParteFinanceiraRepository repository;
    private final ParteFinanceiraMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public ParteFinanceiraResponse criar(ParteFinanceiraRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        if (request != null && StringUtils.hasText(request.getDocumento()) && StringUtils.hasText(request.getTipo())) {
            repository.findByDocumentoAndTipoAndTenant(request.getDocumento().trim(), request.getTipo().trim(), tenantId)
                    .ifPresent(existing -> {
                        throw new ConflictException("Já existe uma parte financeira com o documento/tipo informados");
                    });
        }

        ParteFinanceira entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);

        ParteFinanceira saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ParteFinanceiraResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        ParteFinanceira entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Parte financeira não encontrada com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParteFinanceiraResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public ParteFinanceiraResponse atualizar(UUID id, ParteFinanceiraRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();

        ParteFinanceira entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Parte financeira não encontrada com ID: " + id));

        if (request != null && StringUtils.hasText(request.getDocumento()) && StringUtils.hasText(request.getTipo())) {
            repository.findByDocumentoAndTipoAndTenant(request.getDocumento().trim(), request.getTipo().trim(), tenantId)
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(id)) {
                            throw new ConflictException("Já existe uma parte financeira com o documento/tipo informados");
                        }
                    });
        }

        mapper.updateFromRequest(request, entity);
        ParteFinanceira saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            ParteFinanceira entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Parte financeira não encontrada com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir parte financeira", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            ParteFinanceira entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Parte financeira não encontrada com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Parte financeira já está inativa");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar parte financeira", e);
        }
    }
}

