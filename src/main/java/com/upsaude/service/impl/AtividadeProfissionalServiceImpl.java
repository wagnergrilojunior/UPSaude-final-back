package com.upsaude.service.impl;

import com.upsaude.api.request.profissional.AtividadeProfissionalRequest;
import com.upsaude.api.response.profissional.AtividadeProfissionalResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.profissional.AtividadeProfissional;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.TipoAtividadeProfissionalEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.AtividadeProfissionalRepository;
import com.upsaude.service.profissional.AtividadeProfissionalService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.atividadeprofissional.AtividadeProfissionalCreator;
import com.upsaude.service.support.atividadeprofissional.AtividadeProfissionalDomainService;
import com.upsaude.service.support.atividadeprofissional.AtividadeProfissionalResponseBuilder;
import com.upsaude.service.support.atividadeprofissional.AtividadeProfissionalTenantEnforcer;
import com.upsaude.service.support.atividadeprofissional.AtividadeProfissionalUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtividadeProfissionalServiceImpl implements AtividadeProfissionalService {

    private final AtividadeProfissionalRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final AtividadeProfissionalCreator creator;
    private final AtividadeProfissionalUpdater updater;
    private final AtividadeProfissionalResponseBuilder responseBuilder;
    private final AtividadeProfissionalDomainService domainService;
    private final AtividadeProfissionalTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public AtividadeProfissionalResponse criar(AtividadeProfissionalRequest request) {
        log.debug("Criando nova atividade profissional. Request: {}", request);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            AtividadeProfissional saved = creator.criar(request, tenantId, tenant);
            AtividadeProfissionalResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_ATIVIDADES_PROFISSIONAIS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.atividadeProfissional(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar atividade profissional. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar atividade profissional. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir atividade profissional", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_ATIVIDADES_PROFISSIONAIS, keyGenerator = "atividadeProfissionalCacheKeyGenerator")
    public AtividadeProfissionalResponse obterPorId(UUID id) {
        log.debug("Buscando atividade profissional por ID: {} (cache miss)", id);

        if (id == null) {
            throw new BadRequestException("ID da atividade profissional é obrigatório");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            AtividadeProfissional entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
            return responseBuilder.build(entity);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar atividade profissional. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar atividade profissional", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtividadeProfissionalResponse> listar(Pageable pageable,
                                                     UUID profissionalId,
                                                     UUID medicoId,
                                                     UUID estabelecimentoId,
                                                     TipoAtividadeProfissionalEnum tipoAtividade,
                                                     OffsetDateTime dataInicio,
                                                     OffsetDateTime dataFim) {
        log.debug("Listando atividades profissionais. pageable: {}, profissionalId: {}, medicoId: {}, estabelecimentoId: {}, tipo: {}, dataInicio: {}, dataFim: {}",
            pageable, profissionalId, medicoId, estabelecimentoId, tipoAtividade, dataInicio, dataFim);

        try {
            UUID tenantId = tenantService.validarTenantAtual();

            Page<AtividadeProfissional> page;
            if (profissionalId != null && dataInicio != null && dataFim != null) {
                page = repository.findByProfissionalIdAndDataHoraBetweenAndTenantIdOrderByDataHoraDesc(profissionalId, dataInicio, dataFim, tenantId, pageable);
            } else if (profissionalId != null && tipoAtividade != null) {
                page = repository.findByProfissionalIdAndTipoAtividadeAndTenantIdOrderByDataHoraDesc(profissionalId, tipoAtividade, tenantId, pageable);
            } else if (profissionalId != null) {
                page = repository.findByProfissionalIdAndTenantIdOrderByDataHoraDesc(profissionalId, tenantId, pageable);
            } else if (medicoId != null) {
                page = repository.findByMedicoIdAndTenantIdOrderByDataHoraDesc(medicoId, tenantId, pageable);
            } else if (estabelecimentoId != null) {
                page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataHoraDesc(estabelecimentoId, tenantId, pageable);
            } else if (tipoAtividade != null) {
                page = repository.findByTipoAtividadeAndTenantIdOrderByDataHoraDesc(tipoAtividade, tenantId, pageable);
            } else {
                page = repository.findAllByTenant(tenantId, pageable);
            }

            return page.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar atividades profissionais", e);
            throw new InternalServerErrorException("Erro ao listar atividades profissionais", e);
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_ATIVIDADES_PROFISSIONAIS, keyGenerator = "atividadeProfissionalCacheKeyGenerator")
    public AtividadeProfissionalResponse atualizar(UUID id, AtividadeProfissionalRequest request) {
        log.debug("Atualizando atividade profissional. ID: {}, Request: {}", id, request);

        if (id == null) {
            throw new BadRequestException("ID da atividade profissional é obrigatório");
        }
        if (request == null) {
            throw new BadRequestException("Dados da atividade profissional são obrigatórios");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            AtividadeProfissional updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar atividade profissional. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao atualizar atividade profissional", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_ATIVIDADES_PROFISSIONAIS, keyGenerator = "atividadeProfissionalCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo atividade profissional. ID: {}", id);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir atividade profissional. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir atividade profissional", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da atividade profissional é obrigatório");
        }

        AtividadeProfissional entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Atividade profissional excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}

