package com.upsaude.service.impl.api.profissional.equipe;

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

import com.upsaude.api.request.profissional.equipe.EscalaTrabalhoRequest;
import com.upsaude.api.response.profissional.equipe.EscalaTrabalhoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.profissional.equipe.EscalaTrabalho;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.profissional.equipe.EscalaTrabalhoRepository;
import com.upsaude.service.api.profissional.equipe.EscalaTrabalhoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.escalatrabalho.EscalaTrabalhoCreator;
import com.upsaude.service.api.support.escalatrabalho.EscalaTrabalhoDomainService;
import com.upsaude.service.api.support.escalatrabalho.EscalaTrabalhoResponseBuilder;
import com.upsaude.service.api.support.escalatrabalho.EscalaTrabalhoTenantEnforcer;
import com.upsaude.service.api.support.escalatrabalho.EscalaTrabalhoUpdater;
import org.springframework.dao.DataAccessException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EscalaTrabalhoServiceImpl implements EscalaTrabalhoService {

    private final EscalaTrabalhoRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final EscalaTrabalhoCreator creator;
    private final EscalaTrabalhoUpdater updater;
    private final EscalaTrabalhoResponseBuilder responseBuilder;
    private final EscalaTrabalhoTenantEnforcer tenantEnforcer;
    private final EscalaTrabalhoDomainService domainService;

    @Override
    @Transactional
    public EscalaTrabalhoResponse criar(EscalaTrabalhoRequest request) {
        log.debug("Criando nova escala de trabalho");

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            EscalaTrabalho saved = creator.criar(request, tenantId, tenant);
            EscalaTrabalhoResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_ESCALA_TRABALHO);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.escalaTrabalho(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar escala de trabalho", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_ESCALA_TRABALHO, keyGenerator = "escalaTrabalhoCacheKeyGenerator")
    public EscalaTrabalhoResponse obterPorId(UUID id) {
        log.debug("Buscando escala de trabalho por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da escala de trabalho é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        EscalaTrabalho entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EscalaTrabalhoResponse> listar(Pageable pageable, UUID profissionalId, UUID medicoId, UUID estabelecimentoId, DayOfWeek diaSemana, LocalDate vigentesEm, Boolean apenasAtivos) {
        log.debug("Listando escalas de trabalho paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        UUID tenantId = tenantService.validarTenantAtual();
        Page<EscalaTrabalho> page;

        if (profissionalId != null) {
            page = repository.findByProfissionalIdAndTenantIdOrderByDiaSemanaAscHoraInicioAsc(profissionalId, tenantId, pageable);
        } else if (medicoId != null) {
            page = repository.findByMedicoIdAndTenantIdOrderByDiaSemanaAscHoraInicioAsc(medicoId, tenantId, pageable);
        } else if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantIdOrderByDiaSemanaAscHoraInicioAsc(estabelecimentoId, tenantId, pageable);
        } else if (diaSemana != null) {
            page = repository.findByDiaSemanaAndTenantIdOrderByHoraInicioAsc(diaSemana, tenantId, pageable);
        } else if (vigentesEm != null) {
            page = repository.findVigentesEmAndTenantIdOrderByDiaSemanaAscHoraInicioAsc(vigentesEm, tenantId, pageable);
        } else if (Boolean.TRUE.equals(apenasAtivos)) {
            page = repository.findByActiveTrueAndTenantIdOrderByDiaSemanaAscHoraInicioAsc(tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_ESCALA_TRABALHO, keyGenerator = "escalaTrabalhoCacheKeyGenerator")
    public EscalaTrabalhoResponse atualizar(UUID id, EscalaTrabalhoRequest request) {
        log.debug("Atualizando escala de trabalho. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da escala de trabalho é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        EscalaTrabalho updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_ESCALA_TRABALHO, keyGenerator = "escalaTrabalhoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo escala de trabalho permanentemente. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            EscalaTrabalho entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(Objects.requireNonNull(entity));
            log.info("Escala de trabalho excluída permanentemente com sucesso. ID: {}", id);
        } catch (BadRequestException | com.upsaude.exception.NotFoundException e) {
            log.warn("Erro de validação ao excluir EscalaTrabalho. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir EscalaTrabalho. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir EscalaTrabalho", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_ESCALA_TRABALHO, keyGenerator = "escalaTrabalhoCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando escala de trabalho. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | com.upsaude.exception.NotFoundException e) {
            log.warn("Erro de validação ao inativar EscalaTrabalho. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar EscalaTrabalho. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar EscalaTrabalho", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da escala de trabalho é obrigatório");
        }

        EscalaTrabalho entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Escala de trabalho inativada com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
