package com.upsaude.service.impl.api.profissional;

import java.util.List;
import java.util.UUID;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.api.request.profissional.MedicosRequest;
import com.upsaude.api.response.profissional.EspecialidadeResponse;
import com.upsaude.api.response.profissional.MedicosResponse;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;
import com.upsaude.exception.ConflictException;
import com.upsaude.mapper.profissional.EspecialidadeMapper;
import com.upsaude.repository.referencia.sigtap.SigtapCboRepository;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.MedicosRepository;
import com.upsaude.service.api.profissional.MedicosService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.medico.MedicoCreator;
import com.upsaude.service.api.support.medico.MedicoDomainService;
import com.upsaude.service.api.support.medico.MedicoResponseBuilder;
import com.upsaude.service.api.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.api.support.medico.MedicoUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicosServiceImpl implements MedicosService {

    private final MedicosRepository medicosRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;
    private final MedicoCreator medicoCreator;
    private final MedicoUpdater medicoUpdater;
    private final MedicoTenantEnforcer tenantEnforcer;
    private final MedicoResponseBuilder responseBuilder;
    private final MedicoDomainService domainService;
    private final SigtapCboRepository cboRepository;
    private final EspecialidadeMapper especialidadeMapper;

    @Override
    @Transactional
    public MedicosResponse criar(MedicosRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            Medicos medico = medicoCreator.criar(request, tenantId, tenant);
            MedicosResponse response = responseBuilder.build(medico);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_MEDICOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.medico(tenantId, medico.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar Medico. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar Medico. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir Medico", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_MEDICOS, keyGenerator = "medicoCacheKeyGenerator")
    public MedicosResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do médico é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Medicos medico = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(medico);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicosResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Medicos> medicos = medicosRepository.findAllByTenant(tenantId, pageable);
        return medicos.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_MEDICOS, keyGenerator = "medicoCacheKeyGenerator")
    public MedicosResponse atualizar(UUID id, MedicosRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            Medicos medico = medicoUpdater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(medico);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao atualizar Medico. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar Medico. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao atualizar Medico", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_MEDICOS, keyGenerator = "medicoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir Medico. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir Medico. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir Medico", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_MEDICOS, keyGenerator = "medicoCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao inativar Medico. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar Medico. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar Medico", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_MEDICOS, keyGenerator = "medicoCacheKeyGenerator", beforeInvocation = false)
    public void deletarPermanentemente(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Medicos medico = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(medico);
            medicosRepository.delete(Objects.requireNonNull(medico));
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao deletar Medico. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao deletar Medico. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao deletar Medico", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        Medicos medico = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(medico);
        medico.setActive(false);
        medicosRepository.save(Objects.requireNonNull(medico));
    }

    private Tenant obterTenantAutenticadoOrThrow(UUID tenantId) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
        return tenant;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspecialidadeResponse> listarEspecialidades(UUID medicoId) {
        if (medicoId == null) {
            throw new BadRequestException("ID do médico é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Medicos medico = tenantEnforcer.validarAcessoCompleto(medicoId, tenantId);
        
        return medico.getEspecialidades().stream()
                .map(especialidadeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_MEDICOS, keyGenerator = "medicoCacheKeyGenerator", beforeInvocation = false)
    public EspecialidadeResponse adicionarEspecialidade(UUID medicoId, String codigoCbo) {
        if (medicoId == null) {
            throw new BadRequestException("ID do médico é obrigatório");
        }
        if (codigoCbo == null || codigoCbo.isBlank()) {
            throw new BadRequestException("Código CBO é obrigatório");
        }
        
        UUID tenantId = tenantService.validarTenantAtual();
        Medicos medico = tenantEnforcer.validarAcessoCompleto(medicoId, tenantId);
        
        SigtapOcupacao especialidade = cboRepository.findByCodigoOficial(codigoCbo.trim())
                .orElseThrow(() -> new NotFoundException("CBO não encontrado: " + codigoCbo));
        
        // Verificar se já existe
        boolean jaExiste = medico.getEspecialidades().stream()
                .anyMatch(esp -> esp.getCodigoOficial().equals(codigoCbo.trim()));
        
        if (jaExiste) {
            throw new ConflictException("Especialidade já está associada ao médico");
        }
        
        medico.addEspecialidade(especialidade);
        medicosRepository.save(medico);
        
        log.info("Especialidade {} adicionada ao médico {}", codigoCbo, medicoId);
        return especialidadeMapper.toResponse(especialidade);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_MEDICOS, keyGenerator = "medicoCacheKeyGenerator", beforeInvocation = false)
    public void removerEspecialidade(UUID medicoId, String codigoCbo) {
        if (medicoId == null) {
            throw new BadRequestException("ID do médico é obrigatório");
        }
        if (codigoCbo == null || codigoCbo.isBlank()) {
            throw new BadRequestException("Código CBO é obrigatório");
        }
        
        UUID tenantId = tenantService.validarTenantAtual();
        Medicos medico = tenantEnforcer.validarAcessoCompleto(medicoId, tenantId);
        
        SigtapOcupacao especialidade = medico.getEspecialidades().stream()
                .filter(esp -> esp.getCodigoOficial().equals(codigoCbo.trim()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Especialidade não encontrada para o médico"));
        
        medico.removeEspecialidade(especialidade);
        medicosRepository.save(medico);
        
        log.info("Especialidade {} removida do médico {}", codigoCbo, medicoId);
    }
}
