package com.upsaude.service.impl;

import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.api.request.atendimento.AtendimentoRequest;
import com.upsaude.api.response.atendimento.AtendimentoResponse;
import com.upsaude.entity.atendimento.Atendimento;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.atendimento.AtendimentoRepository;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.service.atendimento.AtendimentoService;
import com.upsaude.service.sistema.TenantService;
import com.upsaude.service.support.atendimento.AtendimentoCreator;
import com.upsaude.service.support.atendimento.AtendimentoResponseBuilder;
import com.upsaude.service.support.atendimento.AtendimentoTenantEnforcer;
import com.upsaude.service.support.atendimento.AtendimentoUpdater;
import com.upsaude.service.support.atendimento.AtendimentoValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtendimentoServiceImpl implements AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;
    private final TenantService tenantService;
    private final CacheManager cacheManager;

    private final AtendimentoValidationService validationService;
    private final AtendimentoTenantEnforcer tenantEnforcer;
    private final AtendimentoCreator creator;
    private final AtendimentoUpdater updater;
    private final AtendimentoResponseBuilder responseBuilder;

    @Override
    @Transactional
    public AtendimentoResponse criar(AtendimentoRequest request) {
        log.debug("Criando novo atendimento. Request: {}", request);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        Atendimento saved = creator.criar(request, tenantId, tenant);
        AtendimentoResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_ATENDIMENTO);
        if (cache != null && response != null && response.getId() != null) {
            cache.put(Objects.requireNonNull((Object) CacheKeyUtil.atendimento(tenantId, response.getId())), response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_ATENDIMENTO, keyGenerator = "atendimentoCacheKeyGenerator")
    public AtendimentoResponse obterPorId(UUID id) {
        log.debug("Buscando atendimento por ID: {} (cache miss)", id);
        validationService.validarId(id);

        UUID tenantId = tenantService.validarTenantAtual();
        Atendimento atendimento = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(atendimento);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoResponse> listar(Pageable pageable) {
        log.debug("Listando atendimentos paginados. Pageable: {}", pageable);
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Atendimento> page = atendimentoRepository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        if (pacienteId == null) {
            log.warn("ID de paciente nulo recebido para listagem de atendimentos");
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Atendimento> page = atendimentoRepository.findByPacienteIdAndTenantIdOrderByInformacoesDataHoraDesc(pacienteId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        if (profissionalId == null) {
            log.warn("ID de profissional nulo recebido para listagem de atendimentos");
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Atendimento> page = atendimentoRepository.findByProfissionalIdAndTenantIdOrderByInformacoesDataHoraDesc(profissionalId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        if (estabelecimentoId == null) {
            log.warn("ID de estabelecimento nulo recebido para listagem de atendimentos");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Atendimento> page = atendimentoRepository.findByEstabelecimentoIdAndTenantIdOrderByInformacoesDataHoraDesc(estabelecimentoId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_ATENDIMENTO, keyGenerator = "atendimentoCacheKeyGenerator")
    public AtendimentoResponse atualizar(UUID id, AtendimentoRequest request) {
        log.debug("Atualizando atendimento. ID: {}, Request: {}", id, request);
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        Atendimento updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_ATENDIMENTO, keyGenerator = "atendimentoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo atendimento. ID: {}", id);
        validationService.validarId(id);

        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        Atendimento entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Atendimento já está inativo");
        }

        entity.setActive(false);
        atendimentoRepository.save(entity);
        log.info("Atendimento excluído (desativado) com sucesso. ID: {}, tenant: {}", id, tenantId);
    }
}
