package com.upsaude.service.impl.api.profissional;

import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.api.request.profissional.ProfissionaisSaudeRequest;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.service.api.profissional.ProfissionaisSaudeService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.profissionaissaude.ProfissionaisSaudeCreator;
import com.upsaude.service.api.support.profissionaissaude.ProfissionaisSaudeDomainService;
import com.upsaude.service.api.support.profissionaissaude.ProfissionaisSaudeResponseBuilder;
import com.upsaude.service.api.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import com.upsaude.service.api.support.profissionaissaude.ProfissionaisSaudeUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfissionaisSaudeServiceImpl implements ProfissionaisSaudeService {

    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;
    private final ProfissionaisSaudeCreator profissionalCreator;
    private final ProfissionaisSaudeUpdater profissionalUpdater;
    private final ProfissionaisSaudeTenantEnforcer tenantEnforcer;
    private final ProfissionaisSaudeResponseBuilder responseBuilder;
    private final ProfissionaisSaudeDomainService domainService;

    @Override
    @Transactional
    public ProfissionaisSaudeResponse criar(ProfissionaisSaudeRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            ProfissionaisSaude profissional = profissionalCreator.criar(request, tenantId, tenant);
            ProfissionaisSaudeResponse response = responseBuilder.build(profissional);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PROFISSIONAIS_SAUDE);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.profissionalSaude(tenantId, profissional.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar ProfissionalSaude. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar ProfissionalSaude. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir ProfissionalSaude", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PROFISSIONAIS_SAUDE, keyGenerator = "profissionaisSaudeCacheKeyGenerator")
    public ProfissionaisSaudeResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do profissional de saúde é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        ProfissionaisSaude profissional = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(profissional);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProfissionaisSaudeResponse> listar(Pageable pageable) {
        log.debug("Listando profissionais de saúde paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Pageable adjustedPageable = ajustarPageableParaCamposEmbeddados(pageable);
            
            Specification<ProfissionaisSaude> spec = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("tenant").get("id"), tenantId));
                return cb.and(predicates.toArray(new Predicate[0]));
            };
            
            Page<ProfissionaisSaude> profissionais = profissionaisSaudeRepository.findAll(spec, adjustedPageable);
            log.debug("Listagem de profissionais de saúde concluída. Total de elementos: {}", profissionais.getTotalElements());
            return profissionais.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar profissionais de saúde. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar profissionais de saúde", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar profissionais de saúde. Pageable: {}", pageable, e);
            throw e;
        }
    }

    private Pageable ajustarPageableParaCamposEmbeddados(Pageable pageable) {
        if (pageable.getSort().isEmpty()) {
            return pageable;
        }

        Sort adjustedSort = pageable.getSort().stream()
                .map(order -> {
                    String property = order.getProperty();
                    // Mapear campos que estão dentro de dadosPessoaisBasicos
                    if ("nomeCompleto".equals(property) || "dataNascimento".equals(property) || "sexo".equals(property)) {
                        property = "dadosPessoaisBasicos." + property;
                    }
                    // Mapear campos que estão dentro de documentosBasicos
                    else if ("cpf".equals(property) || "rg".equals(property) || "cns".equals(property)) {
                        property = "documentosBasicos." + property;
                    }
                    // Mapear campos que estão dentro de registroProfissional
                    else if ("registroProfissional".equals(property) || "conselho".equals(property) || "ufRegistro".equals(property)) {
                        property = "registroProfissional." + property;
                    }
                    // Mapear campos que estão dentro de contato
                    else if ("telefone".equals(property) || "celular".equals(property) ||
                            "email".equals(property) || "telefoneInstitucional".equals(property) ||
                            "emailInstitucional".equals(property)) {
                        property = "contato." + property;
                    }
                    // Mapear campos que estão dentro de dadosDemograficos
                    else if ("estadoCivil".equals(property) || "escolaridade".equals(property) ||
                            "nacionalidade".equals(property) || "naturalidade".equals(property) ||
                            "identidadeGenero".equals(property) || "racaCor".equals(property)) {
                        property = "dadosDemograficos." + property;
                    }
                    return new Sort.Order(order.getDirection(), property);
                })
                .collect(Collectors.collectingAndThen(Collectors.toList(), orders -> {
                    if (orders.isEmpty()) {
                        return Sort.unsorted();
                    }
                    return Sort.by(orders);
                }));

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), adjustedSort);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PROFISSIONAIS_SAUDE, keyGenerator = "profissionaisSaudeCacheKeyGenerator")
    public ProfissionaisSaudeResponse atualizar(UUID id, ProfissionaisSaudeRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);
            ProfissionaisSaude profissional = profissionalUpdater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(profissional);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao atualizar ProfissionalSaude. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar ProfissionalSaude. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao atualizar ProfissionalSaude", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PROFISSIONAIS_SAUDE, keyGenerator = "profissionaisSaudeCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir ProfissionalSaude. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir ProfissionalSaude. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir ProfissionalSaude", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PROFISSIONAIS_SAUDE, keyGenerator = "profissionaisSaudeCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao inativar ProfissionalSaude. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar ProfissionalSaude. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar ProfissionalSaude", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PROFISSIONAIS_SAUDE, keyGenerator = "profissionaisSaudeCacheKeyGenerator", beforeInvocation = false)
    public void deletarPermanentemente(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            ProfissionaisSaude profissional = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(profissional);
            profissionaisSaudeRepository.delete(Objects.requireNonNull(profissional));
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao deletar ProfissionalSaude. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao deletar ProfissionalSaude. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao deletar ProfissionalSaude", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        ProfissionaisSaude profissional = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(profissional);
        profissional.setActive(false);
        profissionaisSaudeRepository.save(Objects.requireNonNull(profissional));
    }

    private Tenant obterTenantAutenticadoOrThrow(UUID tenantId) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
        return tenant;
    }
}
