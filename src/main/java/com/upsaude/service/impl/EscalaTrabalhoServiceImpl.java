package com.upsaude.service.impl;

import com.upsaude.api.request.EscalaTrabalhoRequest;
import com.upsaude.api.response.EscalaTrabalhoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.EscalaTrabalho;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.EscalaTrabalhoRepository;
import com.upsaude.service.EscalaTrabalhoService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.escalatrabalho.EscalaTrabalhoCreator;
import com.upsaude.service.support.escalatrabalho.EscalaTrabalhoResponseBuilder;
import com.upsaude.service.support.escalatrabalho.EscalaTrabalhoTenantEnforcer;
import com.upsaude.service.support.escalatrabalho.EscalaTrabalhoUpdater;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

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

    @Override
    @Transactional
    public EscalaTrabalhoResponse criar(EscalaTrabalhoRequest request) {
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
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar escala de trabalho", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_ESCALA_TRABALHO, keyGenerator = "escalaTrabalhoCacheKeyGenerator")
    public EscalaTrabalhoResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da escala de trabalho é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        EscalaTrabalho entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EscalaTrabalhoResponse> listar(
        Pageable pageable,
        UUID profissionalId,
        UUID medicoId,
        UUID estabelecimentoId,
        DayOfWeek diaSemana,
        LocalDate vigentesEm,
        Boolean apenasAtivos
    ) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<EscalaTrabalho> page = repository.filtrar(
            tenantId,
            profissionalId,
            medicoId,
            estabelecimentoId,
            diaSemana,
            vigentesEm,
            apenasAtivos,
            pageable
        );
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_ESCALA_TRABALHO, keyGenerator = "escalaTrabalhoCacheKeyGenerator")
    public EscalaTrabalhoResponse atualizar(UUID id, EscalaTrabalhoRequest request) {
        if (id == null) {
            throw new BadRequestException("ID da escala de trabalho é obrigatório");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            EscalaTrabalho updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao atualizar escala de trabalho", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_ESCALA_TRABALHO, keyGenerator = "escalaTrabalhoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        inativarInternal(id, tenantId);
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da escala de trabalho é obrigatório");
        }

        EscalaTrabalho entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Escala de trabalho já está inativa");
        }

        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Escala de trabalho excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
