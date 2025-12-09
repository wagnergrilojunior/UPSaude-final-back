package com.upsaude.service.impl;

import com.upsaude.api.request.TenantRequest;
import com.upsaude.api.response.TenantResponse;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.TenantMapper;
import com.upsaude.repository.TenantRepository;
import com.upsaude.repository.UsuariosSistemaRepository;
import com.upsaude.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final UsuariosSistemaRepository usuariosSistemaRepository;

    @Override
    @Transactional
    @CacheEvict(value = "tenants", allEntries = true)
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
    @Transactional(readOnly = true)
    @Cacheable(value = "tenants", key = "#id")
    public TenantResponse obterPorId(UUID id) {
        log.debug("Buscando tenant por ID: {} (cache miss)", id);

        if (id == null) {
            throw new BadRequestException("ID do tenant é obrigatório");
        }

        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + id));
        
        // Inicializa a coleção lazy dentro da transação para evitar LazyInitializationException
        Hibernate.initialize(tenant.getEnderecos());

        return tenantMapper.toResponse(tenant);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TenantResponse> listar(Pageable pageable) {
        log.debug("Listando Tenants paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Tenant> tenants = tenantRepository.findAll(pageable);
        
        // Inicializa as coleções lazy dentro da transação para evitar LazyInitializationException
        tenants.getContent().forEach(tenant -> {
            Hibernate.initialize(tenant.getEnderecos());
        });
        
        // Mapeia dentro da transação para garantir que as coleções lazy estejam acessíveis
        List<TenantResponse> responses = tenants.getContent().stream()
                .map(tenantMapper::toResponse)
                .collect(Collectors.toList());
        
        return new PageImpl<>(responses, tenants.getPageable(), tenants.getTotalElements());
    }

    @Override
    @Transactional
    @CacheEvict(value = "tenants", key = "#id")
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
    @CacheEvict(value = "tenants", key = "#id")
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

    @Override
    public Tenant obterTenantDoUsuarioAutenticado() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                log.warn("Usuário não autenticado. Não é possível obter tenant.");
                return null;
            }

            // Obtém o userId do token JWT
            UUID userId = null;
            Object details = authentication.getDetails();
            if (details instanceof com.upsaude.integration.supabase.SupabaseAuthResponse.User) {
                com.upsaude.integration.supabase.SupabaseAuthResponse.User user = 
                    (com.upsaude.integration.supabase.SupabaseAuthResponse.User) details;
                userId = user.getId();
                log.debug("UserId obtido do SupabaseAuthResponse.User: {}", userId);
            } else if (authentication.getPrincipal() instanceof String) {
                try {
                    userId = UUID.fromString(authentication.getPrincipal().toString());
                    log.debug("UserId obtido do Principal (String): {}", userId);
                } catch (IllegalArgumentException e) {
                    log.warn("Principal não é um UUID válido: {}", authentication.getPrincipal());
                    return null;
                }
            } else {
                log.warn("Tipo de Principal não reconhecido: {}", authentication.getPrincipal() != null ? authentication.getPrincipal().getClass().getName() : "null");
            }

            if (userId != null) {
                java.util.Optional<com.upsaude.entity.UsuariosSistema> usuarioOpt = usuariosSistemaRepository.findByUserId(userId);
                if (usuarioOpt.isPresent()) {
                    com.upsaude.entity.UsuariosSistema usuario = usuarioOpt.get();
                    Tenant tenant = usuario.getTenant();
                    if (tenant != null) {
                        log.debug("Tenant obtido com sucesso: {} (ID: {})", tenant.getNome(), tenant.getId());
                        return tenant;
                    } else {
                        log.warn("Usuário encontrado mas sem tenant associado. UserId: {}", userId);
                    }
                } else {
                    log.warn("Usuário não encontrado no sistema. UserId: {}", userId);
                }
            } else {
                log.warn("Não foi possível obter userId do contexto de autenticação");
            }
        } catch (Exception e) {
            log.error("Erro ao obter tenant do usuário autenticado, Exception: {}", e.getClass().getName(), e);
        }
        return null;
    }
}
