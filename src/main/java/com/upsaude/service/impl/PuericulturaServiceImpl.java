package com.upsaude.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.saude_publica.puericultura.PuericulturaRequest;
import com.upsaude.api.response.saude_publica.puericultura.PuericulturaResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.saude_publica.puericultura.Puericultura;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.saude_publica.puericultura.PuericulturaRepository;
import com.upsaude.service.saude_publica.puericultura.PuericulturaService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.puericultura.PuericulturaCreator;
import com.upsaude.service.support.puericultura.PuericulturaResponseBuilder;
import com.upsaude.service.support.puericultura.PuericulturaTenantEnforcer;
import com.upsaude.service.support.puericultura.PuericulturaUpdater;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PuericulturaServiceImpl implements PuericulturaService {

    private final PuericulturaRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final PuericulturaCreator creator;
    private final PuericulturaUpdater updater;
    private final PuericulturaResponseBuilder responseBuilder;
    private final PuericulturaTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public PuericulturaResponse criar(PuericulturaRequest request) {
        log.debug("Criando nova puericultura");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Puericultura saved = creator.criar(request, tenantId, tenant);
            PuericulturaResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PUERICULTURA);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.puericultura(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar puericultura", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PUERICULTURA, keyGenerator = "puericulturaCacheKeyGenerator")
    public PuericulturaResponse obterPorId(UUID id) {
        log.debug("Buscando puericultura por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da puericultura é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Puericultura entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PuericulturaResponse> listar(Pageable pageable) {
        log.debug("Listando puericulturas paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<Puericultura> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PuericulturaResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Puericultura> page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataInicioAcompanhamentoDesc(estabelecimentoId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PuericulturaResponse> listarPorPaciente(UUID pacienteId) {
        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        List<Puericultura> list = repository.findByPacienteIdAndTenantIdOrderByDataInicioAcompanhamentoDesc(pacienteId, tenantId);
        return list.stream().map(responseBuilder::build).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PuericulturaResponse> listarAtivos(UUID estabelecimentoId, Pageable pageable) {
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Puericultura> page = repository.findByAcompanhamentoAtivoAndEstabelecimentoIdAndTenantId(true, estabelecimentoId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public PuericulturaResponse obterAtivoPorPaciente(UUID pacienteId) {
        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Puericultura entity = repository.findByPacienteIdAndAcompanhamentoAtivoAndTenantId(pacienteId, true, tenantId)
                .orElseThrow(() -> new BadRequestException("Puericultura ativa não encontrada para o paciente"));
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PuericulturaResponse> listarPorPeriodo(UUID estabelecimentoId, LocalDate inicio, LocalDate fim, Pageable pageable) {
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        if (inicio == null || fim == null) {
            throw new BadRequestException("Data de início e fim são obrigatórias");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Puericultura> page = repository.findByEstabelecimentoIdAndDataInicioAcompanhamentoBetweenAndTenantIdOrderByDataInicioAcompanhamentoDesc(estabelecimentoId, inicio, fim, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PUERICULTURA, keyGenerator = "puericulturaCacheKeyGenerator")
    public PuericulturaResponse atualizar(UUID id, PuericulturaRequest request) {
        log.debug("Atualizando puericultura. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da puericultura é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Puericultura updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PUERICULTURA, keyGenerator = "puericulturaCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo puericultura. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da puericultura é obrigatório");
        }

        Puericultura entity = tenantEnforcer.validarAcesso(id, tenantId);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Puericultura excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
