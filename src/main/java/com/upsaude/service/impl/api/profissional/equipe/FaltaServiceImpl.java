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

import com.upsaude.api.request.profissional.equipe.FaltaRequest;
import com.upsaude.api.response.profissional.equipe.FaltaResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.profissional.equipe.Falta;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.TipoFaltaEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.profissional.equipe.FaltaRepository;
import com.upsaude.service.api.profissional.equipe.FaltaService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.mapper.profissional.equipe.FaltaMapper;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.repository.profissional.MedicosRepository;
import com.upsaude.exception.NotFoundException;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaltaServiceImpl implements FaltaService {

    private final FaltaRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;
    private final FaltaMapper mapper;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final MedicosRepository medicosRepository;

    @Override
    @Transactional
    public FaltaResponse criar(FaltaRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Falta entity = mapper.fromRequest(request);
            entity.setActive(true);
            resolverRelacionamentos(entity, request, tenantId, tenant);

            Falta saved = repository.save(entity);
            FaltaResponse response = mapper.toResponse(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_FALTAS);
            if (cache != null) {
                cache.put(Objects.requireNonNull((Object) CacheKeyUtil.falta(tenantId, saved.getId())), response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar falta", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_FALTAS, keyGenerator = "faltasCacheKeyGenerator")
    public FaltaResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da falta é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Falta entity = validarAcesso(id, tenantId);
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FaltaResponse> listar(Pageable pageable, UUID profissionalId, UUID medicoId, UUID estabelecimentoId,
            TipoFaltaEnum tipoFalta, LocalDate dataInicio, LocalDate dataFim) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Falta> page;

        if (profissionalId != null) {
            page = repository.findByProfissionalIdAndTenantIdOrderByDataFaltaDesc(profissionalId, tenantId, pageable);
        } else if (medicoId != null) {
            page = repository.findByMedicoIdAndTenantIdOrderByDataFaltaDesc(medicoId, tenantId, pageable);
        } else if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataFaltaDesc(estabelecimentoId, tenantId,
                    pageable);
        } else if (tipoFalta != null) {
            page = repository.findByTipoFaltaAndTenantIdOrderByDataFaltaDesc(tipoFalta, tenantId, pageable);
        } else if (dataInicio != null && dataFim != null) {
            page = repository.findByDataFaltaBetweenAndTenantIdOrderByDataFaltaDesc(dataInicio, dataFim, tenantId,
                    pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(mapper::toResponse);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_FALTAS, keyGenerator = "faltasCacheKeyGenerator")
    public FaltaResponse atualizar(UUID id, FaltaRequest request) {
        if (id == null) {
            throw new BadRequestException("ID da falta é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Falta entity = validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);
        resolverRelacionamentos(entity, request, tenantId, tenant);

        Falta updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_FALTAS, keyGenerator = "faltasCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Falta entity = validarAcesso(id, tenantId);
            repository.delete(Objects.requireNonNull(entity));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro ao excluir Falta", e);
            throw new InternalServerErrorException("Erro ao excluir Falta", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_FALTAS, keyGenerator = "faltasCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro ao inativar Falta", e);
            throw new InternalServerErrorException("Erro ao inativar Falta", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da falta é obrigatório");
        }

        Falta entity = validarAcesso(id, tenantId);
        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Falta já está inativa");
        }
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
    }

    private Falta validarAcesso(UUID id, UUID tenantId) {
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Falta não encontrada com ID: " + id));
    }

    private void resolverRelacionamentos(Falta entity, FaltaRequest request, UUID tenantId, Tenant tenant) {
        if (request == null)
            return;
        validarTenantAutenticadoOrThrow(tenantId, tenant);
        entity.setTenant(tenant);

        if (request.getProfissional() != null) {
            entity.setProfissional(profissionaisSaudeRepository.findByIdAndTenant(request.getProfissional(), tenantId)
                    .orElseThrow(() -> new NotFoundException(
                            "Profissional não encontrado com ID: " + request.getProfissional())));
        }

        if (request.getMedico() != null) {
            entity.setMedico(medicosRepository.findByIdAndTenant(request.getMedico(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedico())));
        }
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}
