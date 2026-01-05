package com.upsaude.service.impl.api.clinica.atendimento;

import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.clinica.atendimento.AtendimentoRequest;
import com.upsaude.api.response.clinica.atendimento.AtendimentoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.service.api.clinica.atendimento.AtendimentoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.atendimento.AtendimentoCreator;
import com.upsaude.service.api.support.atendimento.AtendimentoDomainService;
import com.upsaude.service.api.support.atendimento.AtendimentoResponseBuilder;
import com.upsaude.service.api.support.atendimento.AtendimentoTenantEnforcer;
import com.upsaude.service.api.support.atendimento.AtendimentoUpdater;
import com.upsaude.service.api.support.atendimento.AtendimentoValidationService;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import org.springframework.dao.DataAccessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    private final AtendimentoDomainService domainService;

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
        log.debug("Excluindo atendimento permanentemente. ID: {}", id);
        validationService.validarId(id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Atendimento entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            atendimentoRepository.delete(Objects.requireNonNull(entity));
            log.info("Atendimento excluído permanentemente com sucesso. ID: {}, tenant: {}", id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir Atendimento. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir Atendimento. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir Atendimento", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_ATENDIMENTO, keyGenerator = "atendimentoCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando atendimento. ID: {}", id);
        validationService.validarId(id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao inativar Atendimento. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar Atendimento. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar Atendimento", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        Atendimento entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        atendimentoRepository.save(entity);
        log.info("Atendimento inativado com sucesso. ID: {}, tenant: {}", id, tenantId);
    }
}
