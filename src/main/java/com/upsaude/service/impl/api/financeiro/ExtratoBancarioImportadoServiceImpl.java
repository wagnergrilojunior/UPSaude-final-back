package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.ExtratoBancarioImportadoRequest;
import com.upsaude.api.response.financeiro.ExtratoBancarioImportadoResponse;
import com.upsaude.entity.financeiro.ContaFinanceira;
import com.upsaude.entity.financeiro.ExtratoBancarioImportado;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.financeiro.ExtratoBancarioImportadoMapper;
import com.upsaude.repository.financeiro.ContaFinanceiraRepository;
import com.upsaude.repository.financeiro.ExtratoBancarioImportadoRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.ExtratoBancarioImportadoService;
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
public class ExtratoBancarioImportadoServiceImpl implements ExtratoBancarioImportadoService {

    private final ExtratoBancarioImportadoRepository repository;
    private final ContaFinanceiraRepository contaFinanceiraRepository;
    private final ExtratoBancarioImportadoMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public ExtratoBancarioImportadoResponse criar(ExtratoBancarioImportadoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        ContaFinanceira conta = contaFinanceiraRepository.findByIdAndTenant(request.getContaFinanceira(), tenantId)
                .orElseThrow(() -> new BadRequestException("Conta financeira não encontrada com ID: " + request.getContaFinanceira()));

        ExtratoBancarioImportado entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        entity.setContaFinanceira(conta);

        ExtratoBancarioImportado saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ExtratoBancarioImportadoResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        ExtratoBancarioImportado entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Extrato importado não encontrado com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExtratoBancarioImportadoResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public ExtratoBancarioImportadoResponse atualizar(UUID id, ExtratoBancarioImportadoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        ExtratoBancarioImportado entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Extrato importado não encontrado com ID: " + id));
        mapper.updateFromRequest(request, entity);
        ExtratoBancarioImportado saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            ExtratoBancarioImportado entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Extrato importado não encontrado com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir extrato importado", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            ExtratoBancarioImportado entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Extrato importado não encontrado com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Extrato importado já está inativo");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar extrato importado", e);
        }
    }
}

