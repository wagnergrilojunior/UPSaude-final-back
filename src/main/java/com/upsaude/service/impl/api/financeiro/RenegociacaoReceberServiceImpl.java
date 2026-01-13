package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.RenegociacaoReceberRequest;
import com.upsaude.api.response.financeiro.RenegociacaoReceberResponse;
import com.upsaude.entity.financeiro.RenegociacaoReceber;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.financeiro.RenegociacaoReceberMapper;
import com.upsaude.repository.financeiro.RenegociacaoReceberRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.RenegociacaoReceberService;
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
public class RenegociacaoReceberServiceImpl implements RenegociacaoReceberService {

    private final RenegociacaoReceberRepository repository;
    private final RenegociacaoReceberMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public RenegociacaoReceberResponse criar(RenegociacaoReceberRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        RenegociacaoReceber entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        RenegociacaoReceber saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public RenegociacaoReceberResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        RenegociacaoReceber entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Renegociação não encontrada com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RenegociacaoReceberResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public RenegociacaoReceberResponse atualizar(UUID id, RenegociacaoReceberRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        RenegociacaoReceber entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Renegociação não encontrada com ID: " + id));
        mapper.updateFromRequest(request, entity);
        RenegociacaoReceber saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            RenegociacaoReceber entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Renegociação não encontrada com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir renegociação", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            RenegociacaoReceber entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("Renegociação não encontrada com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("Renegociação já está inativa");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar renegociação", e);
        }
    }
}

