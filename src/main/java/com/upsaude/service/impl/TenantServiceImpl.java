package com.upsaude.service.impl;

import com.upsaude.api.request.TenantRequest;
import com.upsaude.api.response.TenantResponse;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.TenantMapper;
import com.upsaude.repository.TenantRepository;
import com.upsaude.service.TenantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Tenant.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

    @Override
    @Transactional
    public TenantResponse criar(TenantRequest request) {
        log.debug("Criando novo tenant");

        validarDadosBasicos(request);

        Tenant tenant = tenantMapper.fromRequest(request);
        tenant.setActive(true);

        Tenant tenantSalvo = tenantRepository.save(tenant);
        log.info("Tenant criado com sucesso. ID: {}", tenantSalvo.getId());

        return tenantMapper.toResponse(tenantSalvo);
    }

    @Override
    @Transactional
    public TenantResponse obterPorId(UUID id) {
        log.debug("Buscando tenant por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do tenant é obrigatório");
        }

        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + id));

        return tenantMapper.toResponse(tenant);
    }

    @Override
    public Page<TenantResponse> listar(Pageable pageable) {
        log.debug("Listando Tenants paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Tenant> tenants = tenantRepository.findAll(pageable);
        return tenants.map(tenantMapper::toResponse);
    }

    @Override
    @Transactional
    public TenantResponse atualizar(UUID id, TenantRequest request) {
        log.debug("Atualizando tenant. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do tenant é obrigatório");
        }

        validarDadosBasicos(request);

        Tenant tenantExistente = tenantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + id));

        atualizarDadosTenant(tenantExistente, request);

        Tenant tenantAtualizado = tenantRepository.save(tenantExistente);
        log.info("Tenant atualizado com sucesso. ID: {}", tenantAtualizado.getId());

        return tenantMapper.toResponse(tenantAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo tenant. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do tenant é obrigatório");
        }

        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(tenant.getActive())) {
            throw new BadRequestException("Tenant já está inativo");
        }

        tenant.setActive(false);
        tenantRepository.save(tenant);
        log.info("Tenant excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(TenantRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do tenant são obrigatórios");
        }
    }

        private void atualizarDadosTenant(Tenant tenant, TenantRequest request) {
        Tenant tenantAtualizado = tenantMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = tenant.getId();
        com.upsaude.entity.Tenant tenantOriginal = tenant.getTenant();
        Boolean activeOriginal = tenant.getActive();
        java.time.OffsetDateTime createdAtOriginal = tenant.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(tenantAtualizado, tenant);
        
        // Restaura campos de controle
        tenant.setId(idOriginal);
        tenant.setTenant(tenantOriginal);
        tenant.setActive(activeOriginal);
        tenant.setCreatedAt(createdAtOriginal);
    }
}
