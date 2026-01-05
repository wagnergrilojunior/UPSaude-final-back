package com.upsaude.service.impl.api.paciente;

import com.upsaude.api.request.paciente.DadosSociodemograficosRequest;
import com.upsaude.api.response.paciente.DadosSociodemograficosResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.paciente.DadosSociodemograficos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.DadosSociodemograficosRepository;
import com.upsaude.service.api.paciente.DadosSociodemograficosService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.dadossociodemograficos.DadosSociodemograficosCreator;
import com.upsaude.service.api.support.dadossociodemograficos.DadosSociodemograficosDomainService;
import com.upsaude.service.api.support.dadossociodemograficos.DadosSociodemograficosResponseBuilder;
import com.upsaude.service.api.support.dadossociodemograficos.DadosSociodemograficosTenantEnforcer;
import com.upsaude.service.api.support.dadossociodemograficos.DadosSociodemograficosUpdater;
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
public class DadosSociodemograficosServiceImpl implements DadosSociodemograficosService {

    private final DadosSociodemograficosRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;

    private final DadosSociodemograficosCreator creator;
    private final DadosSociodemograficosUpdater updater;
    private final DadosSociodemograficosTenantEnforcer tenantEnforcer;
    private final DadosSociodemograficosResponseBuilder responseBuilder;
    private final DadosSociodemograficosDomainService domainService;

    @Override
    @Transactional
    public DadosSociodemograficosResponse criar(DadosSociodemograficosRequest request) {
        log.debug("Criando dados sociodemográficos. Request: {}", request);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            DadosSociodemograficos saved = creator.criar(request, tenantId, tenant);
            DadosSociodemograficosResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_DADOS_SOCIODEMOGRAFICOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.dadosSociodemograficos(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | ConflictException | NotFoundException e) {
            log.warn("Erro de validação ao criar dados sociodemográficos. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar DadosSociodemograficos. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir DadosSociodemograficos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar DadosSociodemograficos. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_DADOS_SOCIODEMOGRAFICOS, keyGenerator = "dadosSociodemograficosCacheKeyGenerator")
    public DadosSociodemograficosResponse obterPorId(UUID id) {
        log.debug("Buscando dados sociodemográficos por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de dados sociodemográficos");
            throw new BadRequestException("ID é obrigatório");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            DadosSociodemograficos entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);

            log.debug("Dados sociodemográficos encontrados. ID: {}", id);
            return responseBuilder.build(entity);
        } catch (NotFoundException e) {
            log.warn("Dados sociodemográficos não encontrados. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar DadosSociodemograficos. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao buscar DadosSociodemograficos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar DadosSociodemograficos. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DadosSociodemograficosResponse obterPorPacienteId(UUID pacienteId) {
        log.debug("Buscando dados sociodemográficos por paciente ID: {}", pacienteId);

        if (pacienteId == null) {
            log.warn("ID de paciente nulo recebido para busca de dados sociodemográficos");
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            DadosSociodemograficos entity = repository.findByPacienteIdAndTenantId(pacienteId, tenantId)
                .orElseThrow(() -> new NotFoundException("Dados sociodemográficos não encontrados para o paciente: " + pacienteId));

            log.debug("Dados sociodemográficos encontrados para paciente. Paciente ID: {}", pacienteId);
            return responseBuilder.build(entity);
        } catch (NotFoundException e) {
            log.warn("Dados sociodemográficos não encontrados para paciente. Paciente ID: {}", pacienteId);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar DadosSociodemograficos por paciente. Paciente ID: {}, Exception: {}", pacienteId, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao buscar DadosSociodemograficos do paciente", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar DadosSociodemograficos por paciente. Paciente ID: {}, Exception: {}", pacienteId, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosSociodemograficosResponse> listar(Pageable pageable) {
        log.debug("Listando dados sociodemográficos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Page<DadosSociodemograficos> entities = repository.findAllByTenant(tenantId, pageable);
            log.debug("Listagem de dados sociodemográficos concluída. Total de elementos: {}", entities.getTotalElements());
            return entities.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar DadosSociodemograficos. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao listar DadosSociodemograficos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar DadosSociodemograficos. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_DADOS_SOCIODEMOGRAFICOS, keyGenerator = "dadosSociodemograficosCacheKeyGenerator")
    public DadosSociodemograficosResponse atualizar(UUID id, DadosSociodemograficosRequest request) {
        log.debug("Atualizando dados sociodemográficos. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de dados sociodemográficos");
            throw new BadRequestException("ID é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de dados sociodemográficos. ID: {}", id);
            throw new BadRequestException("Dados sociodemográficos são obrigatórios");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            DadosSociodemograficos updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar dados sociodemográficos não existentes. ID: {}", id);
            throw e;
        } catch (BadRequestException | ConflictException e) {
            log.warn("Erro de validação ao atualizar dados sociodemográficos. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar DadosSociodemograficos. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao atualizar DadosSociodemograficos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar DadosSociodemograficos. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_DADOS_SOCIODEMOGRAFICOS, keyGenerator = "dadosSociodemograficosCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo dados sociodemográficos permanentemente. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            DadosSociodemograficos entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(Objects.requireNonNull(entity));
            log.info("Dados sociodemográficos excluídos permanentemente. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir dados sociodemográficos não existentes. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir dados sociodemográficos. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir DadosSociodemograficos. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir DadosSociodemograficos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir DadosSociodemograficos. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_DADOS_SOCIODEMOGRAFICOS, keyGenerator = "dadosSociodemograficosCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando dados sociodemográficos. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (NotFoundException e) {
            log.warn("Tentativa de inativar dados sociodemográficos não existentes. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao inativar dados sociodemográficos. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar DadosSociodemograficos. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar DadosSociodemograficos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao inativar DadosSociodemograficos. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            log.warn("ID nulo recebido para inativação de dados sociodemográficos");
            throw new BadRequestException("ID é obrigatório");
        }

        DadosSociodemograficos entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Dados sociodemográficos inativados. ID: {}", id);
    }

    private Tenant obterTenantAutenticadoOrThrow(UUID tenantId) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
        return tenant;
    }
}
