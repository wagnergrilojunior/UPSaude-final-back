package com.upsaude.service.impl;

import com.upsaude.api.request.PuericulturaRequest;
import com.upsaude.api.response.PuericulturaResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.Puericultura;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.PuericulturaRepository;
import com.upsaude.service.PuericulturaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.service.TenantService;
import com.upsaude.service.support.puericultura.PuericulturaCreator;
import com.upsaude.service.support.puericultura.PuericulturaDomainService;
import com.upsaude.service.support.puericultura.PuericulturaResponseBuilder;
import com.upsaude.service.support.puericultura.PuericulturaTenantEnforcer;
import com.upsaude.service.support.puericultura.PuericulturaUpdater;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PuericulturaServiceImpl implements PuericulturaService {

    private final PuericulturaRepository puericulturaRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final PuericulturaCreator creator;
    private final PuericulturaUpdater updater;
    private final PuericulturaResponseBuilder responseBuilder;
    private final PuericulturaDomainService domainService;
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
        } catch (BadRequestException | NotFoundException e) {
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
        log.debug("Listando puericulturas paginadas");

        UUID tenantId = tenantService.validarTenantAtual();
        Page<Puericultura> puericulturas = puericulturaRepository.findAllByTenant(tenantId, pageable);
        return puericulturas.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PuericulturaResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando puericulturas por estabelecimento: {}", estabelecimentoId);

        UUID tenantId = tenantService.validarTenantAtual();
        Page<Puericultura> puericulturas = puericulturaRepository
            .findByEstabelecimentoIdAndTenantIdOrderByDataInicioAcompanhamentoDesc(estabelecimentoId, tenantId, pageable);
        return puericulturas.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PuericulturaResponse> listarPorPaciente(UUID pacienteId) {
        log.debug("Listando puericulturas por paciente: {}", pacienteId);

        UUID tenantId = tenantService.validarTenantAtual();
        List<Puericultura> puericulturas = puericulturaRepository.findByPacienteIdAndTenantIdOrderByDataInicioAcompanhamentoDesc(pacienteId, tenantId);
        return puericulturas.stream().map(responseBuilder::build).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PuericulturaResponse> listarAtivos(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando puericulturas ativos: {}", estabelecimentoId);

        UUID tenantId = tenantService.validarTenantAtual();
        Page<Puericultura> puericulturas = puericulturaRepository
            .findByAcompanhamentoAtivoAndEstabelecimentoIdAndTenantId(true, estabelecimentoId, tenantId, pageable);
        return puericulturas.map(responseBuilder::build);
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

    @Override
    @Transactional(readOnly = true)
    public PuericulturaResponse obterAtivoPorPaciente(UUID pacienteId) {
        UUID tenantId = tenantService.validarTenantAtual();
        Puericultura entity = puericulturaRepository.findByPacienteIdAndAcompanhamentoAtivoAndTenantId(pacienteId, true, tenantId)
            .orElseThrow(() -> new NotFoundException("Puericultura ativa não encontrada para o paciente: " + pacienteId));
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PuericulturaResponse> listarPorPeriodo(UUID estabelecimentoId, LocalDate inicio, LocalDate fim, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Puericultura> page = puericulturaRepository
            .findByEstabelecimentoIdAndDataInicioAcompanhamentoBetweenAndTenantIdOrderByDataInicioAcompanhamentoDesc(estabelecimentoId, inicio, fim, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da puericultura é obrigatório");
        }

        Puericultura entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        puericulturaRepository.save(Objects.requireNonNull(entity));
        log.info("Puericultura excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
