package com.upsaude.service.impl.api.estabelecimento;

import com.upsaude.api.request.estabelecimento.EstabelecimentosRequest;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.service.api.estabelecimento.EstabelecimentosService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.estabelecimentos.EstabelecimentosCreator;
import com.upsaude.service.api.support.estabelecimentos.EstabelecimentosDomainService;
import com.upsaude.service.api.support.estabelecimentos.EstabelecimentosResponseBuilder;
import com.upsaude.service.api.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import com.upsaude.service.api.support.estabelecimentos.EstabelecimentosUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstabelecimentosServiceImpl implements EstabelecimentosService {

    private final EstabelecimentosRepository estabelecimentosRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final EstabelecimentosCreator creator;
    private final EstabelecimentosUpdater updater;
    private final EstabelecimentosTenantEnforcer tenantEnforcer;
    private final EstabelecimentosResponseBuilder responseBuilder;
    private final EstabelecimentosDomainService domainService;

    @Override
    @Transactional
    public EstabelecimentosResponse criar(EstabelecimentosRequest request) {
        log.debug("Criando novo estabelecimento. Request: {}", request);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Estabelecimentos saved = creator.criar(request, tenantId, tenant);
            EstabelecimentosResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_ESTABELECIMENTOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.estabelecimento(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException | ConflictException e) {
            log.warn("Erro de validação ao criar estabelecimento. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar estabelecimento. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir estabelecimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar estabelecimento. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_ESTABELECIMENTOS, keyGenerator = "estabelecimentosCacheKeyGenerator")
    public EstabelecimentosResponse obterPorId(UUID id) {
        log.debug("Buscando estabelecimento por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de estabelecimento");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Estabelecimentos estabelecimento = tenantEnforcer.validarAcessoCompleto(id, tenantId);

            log.debug("Estabelecimento encontrado. ID: {}", id);
            return responseBuilder.build(estabelecimento);
        } catch (NotFoundException e) {
            log.warn("Estabelecimento não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar estabelecimento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar estabelecimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar estabelecimento. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstabelecimentosResponse> listar(Pageable pageable) {
        log.debug("Listando estabelecimentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Pageable adjustedPageable = ajustarPageableParaCamposEmbeddados(pageable);
            
            Specification<Estabelecimentos> spec = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("tenant").get("id"), tenantId));
                return cb.and(predicates.toArray(new Predicate[0]));
            };
            
            Page<Estabelecimentos> estabelecimentos = estabelecimentosRepository.findAll(spec, adjustedPageable);
            log.debug("Listagem de estabelecimentos concluída. Total de elementos: {}", estabelecimentos.getTotalElements());
            return estabelecimentos.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar estabelecimentos. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar estabelecimentos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar estabelecimentos. Pageable: {}", pageable, e);
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
                    // Mapear campos que estão dentro de dadosIdentificacao
                    if ("nome".equals(property) || "nomeFantasia".equals(property) || 
                        "tipo".equals(property) || "cnes".equals(property) || "cnpj".equals(property) ||
                        "naturezaJuridica".equals(property)) {
                        property = "dadosIdentificacao." + property;
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
    @CachePut(cacheNames = CacheKeyUtil.CACHE_ESTABELECIMENTOS, keyGenerator = "estabelecimentosCacheKeyGenerator")
    public EstabelecimentosResponse atualizar(UUID id, EstabelecimentosRequest request) {
        log.debug("Atualizando estabelecimento. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de estabelecimento");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de estabelecimento. ID: {}", id);
            throw new BadRequestException("Dados do estabelecimento são obrigatórios");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Estabelecimentos updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar estabelecimento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException | ConflictException e) {
            log.warn("Erro de validação ao atualizar estabelecimento. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar estabelecimento. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar estabelecimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar estabelecimento. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_ESTABELECIMENTOS, keyGenerator = "estabelecimentosCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo estabelecimento. ID: {}", id);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir estabelecimento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir estabelecimento. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir estabelecimento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir estabelecimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir estabelecimento. ID: {}", id, e);
            throw e;
        }
    }
    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            log.warn("ID nulo recebido para exclusão de estabelecimento");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Estabelecimentos entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        estabelecimentosRepository.save(Objects.requireNonNull(entity));
        log.info("Estabelecimento excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenant == null || tenant.getId() == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }

}
