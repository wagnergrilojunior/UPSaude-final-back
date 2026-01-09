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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
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
    private final TenantRepository tenantRepository;
    private final ProfissionaisSaudeCreator profissionalCreator;
    private final ProfissionaisSaudeUpdater profissionalUpdater;
    private final ProfissionaisSaudeTenantEnforcer tenantEnforcer;
    private final ProfissionaisSaudeResponseBuilder responseBuilder;
    private final ProfissionaisSaudeDomainService domainService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public ProfissionaisSaudeResponse criar(ProfissionaisSaudeRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            ProfissionaisSaude profissional = profissionalCreator.criar(request, tenantId, tenant);
            ProfissionaisSaude profissionalRecarregado = tenantEnforcer.validarAcessoCompleto(profissional.getId(),
                    tenantId);
            ProfissionaisSaudeResponse response = responseBuilder.build(profissionalRecarregado);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PROFISSIONAIS_SAUDE);
            if (cache != null) {
                Object key = Objects
                        .requireNonNull((Object) CacheKeyUtil.profissionalSaude(tenantId, profissional.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar ProfissionalSaude. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar ProfissionalSaude. Exception: {}", e.getClass().getSimpleName(),
                    e);
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

        if (profissional.getSigtapOcupacao() != null) {
            log.debug("Profissional ID: {} possui sigtapOcupacao ID: {} antes do build",
                    profissional.getId(), profissional.getSigtapOcupacao().getId());
        } else {
            log.debug("Profissional ID: {} não possui sigtapOcupacao (null) antes do build", profissional.getId());
        }

        return responseBuilder.build(profissional);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfissionaisSaudeResponse obterPorCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new BadRequestException("CPF do profissional de saúde é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        ProfissionaisSaude profissional = profissionaisSaudeRepository.findByCpfAndTenantId(cpf, tenantId)
                .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com CPF: " + cpf));

        return responseBuilder.build(profissional);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfissionaisSaudeResponse obterPorCns(String cns) {
        if (cns == null || cns.isBlank()) {
            throw new BadRequestException("CNS do profissional de saúde é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        ProfissionaisSaude profissional = profissionaisSaudeRepository.findByCnsAndTenantId(cns, tenantId)
                .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com CNS: " + cns));

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
            log.debug("Listagem de profissionais de saúde concluída. Total de elementos: {}",
                    profissionais.getTotalElements());
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

                    if ("nomeCompleto".equals(property) || "dataNascimento".equals(property)
                            || "sexo".equals(property)) {
                        property = "dadosPessoaisBasicos." + property;
                    }

                else if ("cpf".equals(property) || "rg".equals(property) || "cns".equals(property)) {
                        property = "documentosBasicos." + property;
                    }

                else if ("registroProfissional".equals(property) || "ufRegistro".equals(property)) {
                        property = "registroProfissional." + property;
                    }

                else if ("telefone".equals(property) || "celular".equals(property) ||
                        "email".equals(property) || "telefoneInstitucional".equals(property) ||
                        "emailInstitucional".equals(property)) {
                        property = "contato." + property;
                    }

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
            ProfissionaisSaude profissionalRecarregado = tenantEnforcer.validarAcessoCompleto(profissional.getId(),
                    tenantId);
            return responseBuilder.build(profissionalRecarregado);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao atualizar ProfissionalSaude. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar ProfissionalSaude. Exception: {}",
                    e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao atualizar ProfissionalSaude", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PROFISSIONAIS_SAUDE, keyGenerator = "profissionaisSaudeCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            ProfissionaisSaude profissional = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(profissional);

            deletarReferenciasRelacionadas(id);

            profissionaisSaudeRepository.delete(Objects.requireNonNull(profissional));
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir ProfissionalSaude. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir ProfissionalSaude. Exception: {}",
                    e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir ProfissionalSaude", e);
        }
    }

    private void deletarReferenciasRelacionadas(UUID profissionalId) {
        try {
            String discoverQuery = "SELECT tc.table_name, kcu.column_name " +
                    "FROM information_schema.table_constraints tc " +
                    "JOIN information_schema.key_column_usage kcu " +
                    "  ON tc.constraint_name = kcu.constraint_name " +
                    "WHERE tc.constraint_type = 'FOREIGN KEY' " +
                    "  AND tc.table_schema = 'public' " +
                    "  AND kcu.table_schema = 'public' " +
                    "  AND EXISTS ( " +
                    "    SELECT 1 FROM information_schema.constraint_column_usage ccu " +
                    "    WHERE ccu.constraint_name = tc.constraint_name " +
                    "      AND ccu.table_name = 'profissionais_saude' " +
                    "      AND ccu.table_schema = 'public' " +
                    "  ) " +
                    "ORDER BY tc.table_name";

            @SuppressWarnings("unchecked")
            List<Object[]> results = entityManager.createNativeQuery(discoverQuery).getResultList();

            deletarConsultasRelacionadasAosAtendimentos(profissionalId);

            for (Object[] row : results) {
                String tableName = (String) row[0];
                String columnName = (String) row[1];

                if ("profissionais_saude".equals(tableName)) {
                    continue;
                }

                deletarTabelaComDependencias(tableName, columnName, profissionalId);
            }

            log.debug("Concluída a exclusão de referências relacionadas ao profissional de saúde ID: {}",
                    profissionalId);
        } catch (Exception e) {
            log.warn(
                    "Erro ao descobrir referências relacionadas ao profissional de saúde ID: {}. Tentando deletar tabelas conhecidas... Erro: {}",
                    profissionalId, e.getMessage());

            deletarTabelasConhecidas(profissionalId);
        }
    }

    private void deletarTabelaComDependencias(String tableName, String columnName, UUID profissionalId) {
        try {
            if ("atendimentos".equals(tableName)) {
                deletarConsultasRelacionadasAosAtendimentos(profissionalId);
            }

            String deleteSql = String.format("DELETE FROM public.%s WHERE %s = :profissionalId", tableName, columnName);
            int deleted = entityManager.createNativeQuery(deleteSql)
                    .setParameter("profissionalId", profissionalId)
                    .executeUpdate();

            if (deleted > 0) {
                log.debug("Deletados {} registros da tabela '{}' referenciando profissional de saúde ID: {}", deleted,
                        tableName, profissionalId);
            }
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            if (errorMsg != null && errorMsg.contains("violates foreign key constraint")
                    && errorMsg.contains("consultas")) {
                try {
                    deletarConsultasRelacionadasAosAtendimentos(profissionalId);

                    String deleteSql = String.format("DELETE FROM public.%s WHERE %s = :profissionalId", tableName,
                            columnName);
                    int deleted = entityManager.createNativeQuery(deleteSql)
                            .setParameter("profissionalId", profissionalId)
                            .executeUpdate();

                    if (deleted > 0) {
                        log.debug("Deletados {} registros da tabela '{}' após remover consultas. Profissional ID: {}",
                                deleted, tableName, profissionalId);
                    }
                } catch (Exception e2) {
                    log.warn("Erro ao deletar atendimentos após remover consultas. Profissional ID: {}. Erro: {}",
                            profissionalId, e2.getMessage());
                }
            } else {
                log.warn(
                        "Erro ao deletar registros da tabela '{}' referenciando profissional de saúde ID: {}. Continuando... Erro: {}",
                        tableName, profissionalId,
                        errorMsg != null ? errorMsg.substring(0, Math.min(200, errorMsg.length()))
                                : "Erro desconhecido");
            }
        }
    }

    private void deletarConsultasRelacionadasAosAtendimentos(UUID profissionalId) {
        try {
            String deleteConsultasSql = "DELETE FROM public.consultas " +
                    "WHERE atendimento_id IN ( " +
                    "    SELECT id FROM public.atendimentos WHERE profissional_id = :profissionalId " +
                    ")";

            int deletedConsultas = entityManager.createNativeQuery(deleteConsultasSql)
                    .setParameter("profissionalId", profissionalId)
                    .executeUpdate();

            if (deletedConsultas > 0) {
                log.debug("Deletadas {} consultas relacionadas aos atendimentos do profissional de saúde ID: {}",
                        deletedConsultas, profissionalId);
            }
        } catch (Exception e) {
            log.trace("Erro ao deletar consultas relacionadas aos atendimentos. Profissional ID: {}. Erro: {}",
                    profissionalId, e.getMessage());
        }
    }

    private void deletarTabelasConhecidas(UUID profissionalId) {
        String[][] tabelasConhecidas = {
                { "profissionais_saude_especialidades", "profissional_saude_id" },
                { "profissionais_saude_especialidades", "profissional_id" },
                { "profissionais_saude_especialidades", "id" },
                { "agendamentos", "profissional_id" },
                { "atendimentos", "profissional_id" }
        };

        for (String[] tabelaInfo : tabelasConhecidas) {
            String tableName = tabelaInfo[0];
            String columnName = tabelaInfo[1];

            try {
                String sql = String.format("DELETE FROM public.%s WHERE %s = :profissionalId", tableName, columnName);
                int deleted = entityManager.createNativeQuery(sql)
                        .setParameter("profissionalId", profissionalId)
                        .executeUpdate();

                if (deleted > 0) {
                    log.debug("Deletados {} registros da tabela '{}' referenciando profissional de saúde ID: {}",
                            deleted, tableName, profissionalId);
                }
            } catch (Exception e) {
                log.trace("Não foi possível deletar da tabela '{}' com coluna '{}': {}", tableName, columnName,
                        e.getMessage());
            }
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
            log.error("Erro de acesso a dados ao inativar ProfissionalSaude. Exception: {}",
                    e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar ProfissionalSaude", e);
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
            // Fallback para testes: buscar tenant do banco quando não houver autenticação
            tenant = tenantRepository.findById(tenantId).orElse(null);
            if (tenant != null && tenant.getId().equals(tenantId)) {
                return tenant;
            }
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
        return tenant;
    }
}
