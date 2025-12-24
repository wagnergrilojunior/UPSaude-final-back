package com.upsaude.service.impl.api.paciente;
import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.paciente.DadosClinicosBasicosRequest;
import com.upsaude.api.response.paciente.DadosClinicosBasicosResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.paciente.DadosClinicosBasicos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.DadosClinicosBasicosRepository;
import com.upsaude.service.api.paciente.DadosClinicosBasicosService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.dadosclinicosbasicos.DadosClinicosBasicosCreator;
import com.upsaude.service.api.support.dadosclinicosbasicos.DadosClinicosBasicosDomainService;
import com.upsaude.service.api.support.dadosclinicosbasicos.DadosClinicosBasicosResponseBuilder;
import com.upsaude.service.api.support.dadosclinicosbasicos.DadosClinicosBasicosTenantEnforcer;
import com.upsaude.service.api.support.dadosclinicosbasicos.DadosClinicosBasicosUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DadosClinicosBasicosServiceImpl implements DadosClinicosBasicosService {

    private final DadosClinicosBasicosRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final DadosClinicosBasicosCreator creator;
    private final DadosClinicosBasicosUpdater updater;
    private final DadosClinicosBasicosTenantEnforcer tenantEnforcer;
    private final DadosClinicosBasicosResponseBuilder responseBuilder;
    private final DadosClinicosBasicosDomainService domainService;

    @Override
    @Transactional
    public DadosClinicosBasicosResponse criar(DadosClinicosBasicosRequest request) {
        log.debug("Criando dados clínicos básicos. Request: {}", request);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            DadosClinicosBasicos saved = creator.criar(request, tenantId, tenant);
            DadosClinicosBasicosResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_DADOS_CLINICOS_BASICOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.dadosClinicosBasicos(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | ConflictException | NotFoundException e) {
            log.warn("Erro de validação ao criar dados clínicos básicos. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar DadosClinicosBasicos. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir DadosClinicosBasicos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar DadosClinicosBasicos. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_DADOS_CLINICOS_BASICOS, keyGenerator = "dadosClinicosBasicosCacheKeyGenerator")
    public DadosClinicosBasicosResponse obterPorId(UUID id) {
        log.debug("Buscando dados clínicos básicos por ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de dados clínicos básicos");
            throw new BadRequestException("ID é obrigatório");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            DadosClinicosBasicos entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);

            log.debug("Dados clínicos básicos encontrados. ID: {}", id);
            return responseBuilder.build(entity);
        } catch (NotFoundException e) {
            log.warn("Dados clínicos básicos não encontrados. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar DadosClinicosBasicos. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao buscar DadosClinicosBasicos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar DadosClinicosBasicos. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DadosClinicosBasicosResponse obterPorPacienteId(UUID pacienteId) {
        log.debug("Buscando dados clínicos básicos por paciente ID: {}", pacienteId);

        if (pacienteId == null) {
            log.warn("ID de paciente nulo recebido para busca de dados clínicos básicos");
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            DadosClinicosBasicos entity = repository.findByPacienteIdAndTenantId(pacienteId, tenantId)
                .orElseThrow(() -> new NotFoundException("Dados clínicos básicos não encontrados para o paciente: " + pacienteId));

            log.debug("Dados clínicos básicos encontrados para paciente. Paciente ID: {}", pacienteId);
            return responseBuilder.build(entity);
        } catch (NotFoundException e) {
            log.warn("Dados clínicos básicos não encontrados para paciente. Paciente ID: {}", pacienteId);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar DadosClinicosBasicos por paciente. Paciente ID: {}, Exception: {}", pacienteId, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao buscar DadosClinicosBasicos do paciente", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar DadosClinicosBasicos por paciente. Paciente ID: {}, Exception: {}", pacienteId, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosClinicosBasicosResponse> listar(Pageable pageable) {
        log.debug("Listando dados clínicos básicos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Page<DadosClinicosBasicos> entities = repository.findAllByTenant(tenantId, pageable);
            log.debug("Listagem de dados clínicos básicos concluída. Total de elementos: {}", entities.getTotalElements());
            return entities.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar DadosClinicosBasicos. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao listar DadosClinicosBasicos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar DadosClinicosBasicos. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_DADOS_CLINICOS_BASICOS, keyGenerator = "dadosClinicosBasicosCacheKeyGenerator")
    public DadosClinicosBasicosResponse atualizar(UUID id, DadosClinicosBasicosRequest request) {
        log.debug("Atualizando dados clínicos básicos. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de dados clínicos básicos");
            throw new BadRequestException("ID é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de dados clínicos básicos. ID: {}", id);
            throw new BadRequestException("Dados clínicos básicos são obrigatórios");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            DadosClinicosBasicos updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar dados clínicos básicos não existentes. ID: {}", id);
            throw e;
        } catch (BadRequestException | ConflictException e) {
            log.warn("Erro de validação ao atualizar dados clínicos básicos. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar DadosClinicosBasicos. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao atualizar DadosClinicosBasicos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar DadosClinicosBasicos. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_DADOS_CLINICOS_BASICOS, keyGenerator = "dadosClinicosBasicosCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo dados clínicos básicos. ID: {}", id);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir dados clínicos básicos não existentes. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir dados clínicos básicos. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir DadosClinicosBasicos. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir DadosClinicosBasicos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir DadosClinicosBasicos. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            log.warn("ID nulo recebido para exclusão de dados clínicos básicos");
            throw new BadRequestException("ID é obrigatório");
        }

        DadosClinicosBasicos entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Dados clínicos básicos excluídos. ID: {}", id);
    }

    private Tenant obterTenantAutenticadoOrThrow(UUID tenantId) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
        return tenant;
    }
}
