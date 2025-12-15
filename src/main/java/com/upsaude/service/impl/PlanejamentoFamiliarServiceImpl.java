package com.upsaude.service.impl;

import com.upsaude.api.request.PlanejamentoFamiliarRequest;
import com.upsaude.api.response.PlanejamentoFamiliarResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.PlanejamentoFamiliar;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.PlanejamentoFamiliarRepository;
import com.upsaude.enums.TipoMetodoContraceptivoEnum;
import com.upsaude.service.PlanejamentoFamiliarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.service.TenantService;
import com.upsaude.service.support.planejamentofamiliar.PlanejamentoFamiliarCreator;
import com.upsaude.service.support.planejamentofamiliar.PlanejamentoFamiliarDomainService;
import com.upsaude.service.support.planejamentofamiliar.PlanejamentoFamiliarResponseBuilder;
import com.upsaude.service.support.planejamentofamiliar.PlanejamentoFamiliarTenantEnforcer;
import com.upsaude.service.support.planejamentofamiliar.PlanejamentoFamiliarUpdater;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanejamentoFamiliarServiceImpl implements PlanejamentoFamiliarService {

    private final PlanejamentoFamiliarRepository planejamentoFamiliarRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final PlanejamentoFamiliarCreator creator;
    private final PlanejamentoFamiliarUpdater updater;
    private final PlanejamentoFamiliarResponseBuilder responseBuilder;
    private final PlanejamentoFamiliarDomainService domainService;
    private final PlanejamentoFamiliarTenantEnforcer tenantEnforcer;

    @Override
    @Transactional
    public PlanejamentoFamiliarResponse criar(PlanejamentoFamiliarRequest request) {
        log.debug("Criando novo planejamento familiar");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            PlanejamentoFamiliar saved = creator.criar(request, tenantId, tenant);
            PlanejamentoFamiliarResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PLANEJAMENTO_FAMILIAR);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.planejamentoFamiliar(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar planejamento familiar", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PLANEJAMENTO_FAMILIAR, keyGenerator = "planejamentoFamiliarCacheKeyGenerator")
    public PlanejamentoFamiliarResponse obterPorId(UUID id) {
        log.debug("Buscando planejamento familiar por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do planejamento familiar é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        PlanejamentoFamiliar entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanejamentoFamiliarResponse> listar(Pageable pageable) {
        log.debug("Listando planejamentos familiares paginados");

        UUID tenantId = tenantService.validarTenantAtual();
        Page<PlanejamentoFamiliar> planejamentos = planejamentoFamiliarRepository.findAllByTenant(tenantId, pageable);
        return planejamentos.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanejamentoFamiliarResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando planejamentos familiares por estabelecimento: {}", estabelecimentoId);

        UUID tenantId = tenantService.validarTenantAtual();
        Page<PlanejamentoFamiliar> planejamentos =
            planejamentoFamiliarRepository.findByEstabelecimentoIdAndTenantIdOrderByDataInicioAcompanhamentoDesc(estabelecimentoId, tenantId, pageable);
        return planejamentos.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanejamentoFamiliarResponse> listarPorPaciente(UUID pacienteId) {
        log.debug("Listando planejamentos familiares por paciente: {}", pacienteId);

        UUID tenantId = tenantService.validarTenantAtual();
        List<PlanejamentoFamiliar> planejamentos = planejamentoFamiliarRepository.findByPacienteIdAndTenantIdOrderByDataInicioAcompanhamentoDesc(pacienteId, tenantId);
        return planejamentos.stream().map(responseBuilder::build).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanejamentoFamiliarResponse> listarAtivos(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando planejamentos familiares ativos: {}", estabelecimentoId);

        UUID tenantId = tenantService.validarTenantAtual();
        Page<PlanejamentoFamiliar> planejamentos =
            planejamentoFamiliarRepository.findByAcompanhamentoAtivoAndEstabelecimentoIdAndTenantId(true, estabelecimentoId, tenantId, pageable);
        return planejamentos.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PLANEJAMENTO_FAMILIAR, keyGenerator = "planejamentoFamiliarCacheKeyGenerator")
    public PlanejamentoFamiliarResponse atualizar(UUID id, PlanejamentoFamiliarRequest request) {
        log.debug("Atualizando planejamento familiar. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do planejamento familiar é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        PlanejamentoFamiliar updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PLANEJAMENTO_FAMILIAR, keyGenerator = "planejamentoFamiliarCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo planejamento familiar. ID: {}", id);

        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanejamentoFamiliarResponse> listarPorMetodo(UUID estabelecimentoId, TipoMetodoContraceptivoEnum metodo, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<PlanejamentoFamiliar> page = planejamentoFamiliarRepository
            .findByMetodoAtualAndEstabelecimentoIdAndTenantId(metodo, estabelecimentoId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public PlanejamentoFamiliarResponse obterAtivoPorPaciente(UUID pacienteId) {
        UUID tenantId = tenantService.validarTenantAtual();
        PlanejamentoFamiliar entity = planejamentoFamiliarRepository
            .findByPacienteIdAndAcompanhamentoAtivoAndTenantId(pacienteId, true, tenantId)
            .orElseThrow(() -> new NotFoundException("Acompanhamento ativo não encontrado para o paciente: " + pacienteId));
        return responseBuilder.build(entity);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do planejamento familiar é obrigatório");
        }

        PlanejamentoFamiliar entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        planejamentoFamiliarRepository.save(Objects.requireNonNull(entity));
        log.info("Planejamento familiar excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
