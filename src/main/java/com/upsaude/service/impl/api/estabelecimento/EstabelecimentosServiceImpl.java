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

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
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

    @PersistenceContext
    private EntityManager entityManager;

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
        log.debug("Excluindo estabelecimento permanentemente. ID: {}", id);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Estabelecimentos entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);

            deletarReferenciasRelacionadas(id);

            estabelecimentosRepository.delete(Objects.requireNonNull(entity));
            log.info("Estabelecimento excluído permanentemente com sucesso. ID: {}", id);
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

    private void deletarReferenciasRelacionadas(UUID estabelecimentoId) {
        try {
            deletarConsultasRelacionadasAosAtendimentos(estabelecimentoId);

            String discoverQuery =
                "SELECT tc.table_name, kcu.column_name " +
                "FROM information_schema.table_constraints tc " +
                "JOIN information_schema.key_column_usage kcu " +
                "  ON tc.constraint_name = kcu.constraint_name " +
                "WHERE tc.constraint_type = 'FOREIGN KEY' " +
                "  AND tc.table_schema = 'public' " +
                "  AND kcu.table_schema = 'public' " +
                "  AND EXISTS ( " +
                "    SELECT 1 FROM information_schema.constraint_column_usage ccu " +
                "    WHERE ccu.constraint_name = tc.constraint_name " +
                "      AND ccu.table_name = 'estabelecimentos' " +
                "      AND ccu.table_schema = 'public' " +
                "  ) " +
                "ORDER BY tc.table_name";

            @SuppressWarnings("unchecked")
            List<Object[]> results = entityManager.createNativeQuery(discoverQuery).getResultList();

            for (Object[] row : results) {
                String tableName = (String) row[0];
                String columnName = (String) row[1];

                try {
                    if ("estabelecimentos".equals(tableName)) {
                        continue;
                    }

                    String deleteSql = String.format("DELETE FROM public.%s WHERE %s = :estabelecimentoId", tableName, columnName);
                    int deleted = entityManager.createNativeQuery(deleteSql)
                            .setParameter("estabelecimentoId", estabelecimentoId)
                            .executeUpdate();

                    if (deleted > 0) {
                        log.debug("Deletados {} registros da tabela '{}' referenciando estabelecimento ID: {}", deleted, tableName, estabelecimentoId);
                    }
                } catch (Exception e) {
                    log.warn("Erro ao deletar registros da tabela '{}' referenciando estabelecimento ID: {}. Continuando... Erro: {}",
                            tableName, estabelecimentoId, e.getMessage());
                }
            }

            log.debug("Concluída a exclusão de referências relacionadas ao estabelecimento ID: {}", estabelecimentoId);
        } catch (Exception e) {
            log.warn("Erro ao descobrir referências relacionadas ao estabelecimento ID: {}. Tentando deletar tabelas conhecidas... Erro: {}",
                    estabelecimentoId, e.getMessage());

            deletarTabelasConhecidas(estabelecimentoId);
        }
    }

    private void deletarConsultasRelacionadasAosAtendimentos(UUID estabelecimentoId) {
        try {
            String deleteConsultasSql =
                "DELETE FROM public.consultas c " +
                "WHERE c.atendimento_id IN ( " +
                "    SELECT a.id FROM public.atendimentos a " +
                "    WHERE a.estabelecimento_id = :estabelecimentoId " +
                ")";
            int deletedConsultas = entityManager.createNativeQuery(deleteConsultasSql)
                    .setParameter("estabelecimentoId", estabelecimentoId)
                    .executeUpdate();
            if (deletedConsultas > 0) {
                log.debug("Deletadas {} consultas relacionadas a atendimentos do estabelecimento ID: {}", deletedConsultas, estabelecimentoId);
            }
        } catch (Exception e) {
            log.warn("Erro ao deletar consultas relacionadas a atendimentos do estabelecimento ID: {}. Erro: {}", estabelecimentoId, e.getMessage());
        }
    }

    private void deletarTabelasConhecidas(UUID estabelecimentoId) {
        String[][] tabelasConhecidas = {
            {"enderecos", "estabelecimento_id"},
            {"equipamentos_estabelecimento", "estabelecimento_id"},
            {"agendamentos", "estabelecimento_id"},
            {"atendimentos", "estabelecimento_id"},
            {"usuarios_estabelecimentos", "estabelecimento_id"}
        };

        for (String[] tabelaInfo : tabelasConhecidas) {
            String tableName = tabelaInfo[0];
            String columnName = tabelaInfo[1];

            try {
                String sql = String.format("DELETE FROM public.%s WHERE %s = :estabelecimentoId", tableName, columnName);
                int deleted = entityManager.createNativeQuery(sql)
                        .setParameter("estabelecimentoId", estabelecimentoId)
                        .executeUpdate();

                if (deleted > 0) {
                    log.debug("Deletados {} registros da tabela '{}' referenciando estabelecimento ID: {}", deleted, tableName, estabelecimentoId);
                }
            } catch (Exception e) {
                log.trace("Não foi possível deletar da tabela '{}' com coluna '{}': {}", tableName, columnName, e.getMessage());
            }
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_ESTABELECIMENTOS, keyGenerator = "estabelecimentosCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando estabelecimento. ID: {}", id);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (NotFoundException e) {
            log.warn("Tentativa de inativar estabelecimento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao inativar estabelecimento. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar estabelecimento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao inativar estabelecimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao inativar estabelecimento. ID: {}", id, e);
            throw e;
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            log.warn("ID nulo recebido para inativação de estabelecimento");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Estabelecimentos entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        estabelecimentosRepository.save(Objects.requireNonNull(entity));
        log.info("Estabelecimento inativado com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenant == null || tenant.getId() == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }

}
