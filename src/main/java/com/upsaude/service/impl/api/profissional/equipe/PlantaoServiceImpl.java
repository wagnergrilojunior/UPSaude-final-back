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

import com.upsaude.api.request.profissional.equipe.PlantaoRequest;
import com.upsaude.api.response.profissional.equipe.PlantaoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.profissional.equipe.Plantao;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.TipoPlantaoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.profissional.equipe.PlantaoRepository;
import com.upsaude.service.api.profissional.equipe.PlantaoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.mapper.profissional.equipe.PlantaoMapper;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.repository.profissional.MedicosRepository;
import com.upsaude.exception.NotFoundException;
import org.springframework.dao.DataAccessException;

import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantaoServiceImpl implements PlantaoService {

    private final PlantaoRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;
    private final PlantaoMapper mapper;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final MedicosRepository medicosRepository;

    @Override
    @Transactional
    public PlantaoResponse criar(PlantaoRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Plantao entity = mapper.fromRequest(request);
            entity.setActive(true);
            resolverRelacionamentos(entity, request, tenantId, tenant);

            Plantao saved = repository.save(entity);
            PlantaoResponse response = mapper.toResponse(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PLANTOES);
            if (cache != null) {
                cache.put(Objects.requireNonNull((Object) CacheKeyUtil.plantao(tenantId, saved.getId())), response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar plantão", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PLANTOES, keyGenerator = "plantoesCacheKeyGenerator")
    public PlantaoResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do plantão é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Plantao entity = validarAcesso(id, tenantId);
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlantaoResponse> listar(Pageable pageable, UUID profissionalId, UUID medicoId, UUID estabelecimentoId,
            TipoPlantaoEnum tipoPlantao, OffsetDateTime dataInicio, OffsetDateTime dataFim, Boolean emAndamento) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Plantao> page;

        if (profissionalId != null) {
            page = repository.findByProfissionalIdAndTenantIdOrderByDataHoraInicioDesc(profissionalId, tenantId,
                    pageable);
        } else if (medicoId != null) {
            page = repository.findByMedicoIdAndTenantIdOrderByDataHoraInicioDesc(medicoId, tenantId, pageable);
        } else if (estabelecimentoId != null) {
            page = repository.findByEstabelecimentoIdAndTenantIdOrderByDataHoraInicioDesc(estabelecimentoId, tenantId,
                    pageable);
        } else if (tipoPlantao != null) {
            page = repository.findByTipoPlantaoAndTenantIdOrderByDataHoraInicioDesc(tipoPlantao, tenantId, pageable);
        } else if (dataInicio != null && dataFim != null) {
            page = repository.findByDataHoraInicioBetweenAndTenantIdOrderByDataHoraInicioDesc(dataInicio, dataFim,
                    tenantId, pageable);
        } else if (Boolean.TRUE.equals(emAndamento)) {
            OffsetDateTime agora = OffsetDateTime.now();
            page = repository.findEmAndamentoAndTenantIdOrderByDataHoraInicioDesc(agora, tenantId, pageable);
        } else {
            page = repository.findAllByTenant(tenantId, pageable);
        }

        return page.map(mapper::toResponse);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PLANTOES, keyGenerator = "plantoesCacheKeyGenerator")
    public PlantaoResponse atualizar(UUID id, PlantaoRequest request) {
        if (id == null) {
            throw new BadRequestException("ID do plantão é obrigatório");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        validarTenantAutenticadoOrThrow(tenantId, tenant);

        Plantao entity = validarAcesso(id, tenantId);
        mapper.updateFromRequest(request, entity);
        resolverRelacionamentos(entity, request, tenantId, tenant);

        Plantao updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PLANTOES, keyGenerator = "plantoesCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Plantao entity = validarAcesso(id, tenantId);
            repository.delete(Objects.requireNonNull(entity));
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro ao excluir Plantao", e);
            throw new InternalServerErrorException("Erro ao excluir Plantao", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PLANTOES, keyGenerator = "plantoesCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro ao inativar Plantao", e);
            throw new InternalServerErrorException("Erro ao inativar Plantao", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do plantão é obrigatório");
        }

        Plantao entity = validarAcesso(id, tenantId);
        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Plantão já está inativo");
        }
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
    }

    private Plantao validarAcesso(UUID id, UUID tenantId) {
        return repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Plantão não encontrado com ID: " + id));
    }

    private void resolverRelacionamentos(Plantao entity, PlantaoRequest request, UUID tenantId, Tenant tenant) {
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
