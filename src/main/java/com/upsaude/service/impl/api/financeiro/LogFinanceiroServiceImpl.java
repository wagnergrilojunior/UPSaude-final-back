package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.LogFinanceiroRequest;
import com.upsaude.api.response.financeiro.LogFinanceiroResponse;
import com.upsaude.entity.financeiro.LogFinanceiro;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.financeiro.LogFinanceiroMapper;
import com.upsaude.repository.financeiro.LogFinanceiroRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.LogFinanceiroService;
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
public class LogFinanceiroServiceImpl implements LogFinanceiroService {

    private final LogFinanceiroRepository repository;
    private final LogFinanceiroMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public LogFinanceiroResponse criar(LogFinanceiroRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        LogFinanceiro entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        LogFinanceiro saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public LogFinanceiroResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        LogFinanceiro entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Log financeiro não encontrado com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LogFinanceiroResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public LogFinanceiroResponse atualizar(UUID id, LogFinanceiroRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        LogFinanceiro entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Log financeiro não encontrado com ID: " + id));
        mapper.updateFromRequest(request, entity);
        LogFinanceiro saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            LogFinanceiro entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Log financeiro não encontrado com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir log financeiro", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            LogFinanceiro entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Log financeiro não encontrado com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Log financeiro já está inativo");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar log financeiro", e);
        }
    }
}

