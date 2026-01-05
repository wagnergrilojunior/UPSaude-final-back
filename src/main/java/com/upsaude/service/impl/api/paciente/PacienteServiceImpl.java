package com.upsaude.service.impl.api.paciente;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.api.response.paciente.PacienteSimplificadoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.service.api.paciente.PacienteService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.paciente.PacienteAssociacoesManager;
import com.upsaude.service.api.support.paciente.PacienteCreator;
import com.upsaude.service.api.support.paciente.PacienteDomainService;
import com.upsaude.service.api.support.paciente.PacienteResponseBuilder;
import com.upsaude.service.api.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.api.support.paciente.PacienteUpdater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;
    private final PacienteCreator pacienteCreator;
    private final PacienteUpdater pacienteUpdater;
    private final PacienteAssociacoesManager associacoesManager;
    private final PacienteTenantEnforcer tenantEnforcer;
    private final PacienteResponseBuilder responseBuilder;
    private final PacienteDomainService domainService;

    @Override
    @Transactional
    public PacienteResponse criar(PacienteRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Paciente paciente = pacienteCreator.criar(request, tenantId);
            associacoesManager.processarTodas(paciente, request, tenantId);
            PacienteResponse response = responseBuilder.build(paciente);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_PACIENTES);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.paciente(tenantId, paciente.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar Paciente. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar Paciente. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir Paciente", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_PACIENTES, keyGenerator = "pacienteCacheKeyGenerator")
    public PacienteResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Paciente paciente = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(paciente);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<PacienteResponse> listar(Pageable pageable) {
        // Paciente estende BaseEntityWithoutTenant, então não tem tenant_id
        // Usar findAll padrão do JpaRepository
        UUID tenantId = tenantService.validarTenantAtual(); // Mantido para compatibilidade, mas não usado na query
        Page<Paciente> pacientes = pacienteRepository.findAll(pageable);
        return pacientes.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PacienteSimplificadoResponse> listarSimplificado(Pageable pageable) {
        tenantService.validarTenantAtual();
        // Paciente é BaseEntityWithoutTenant: listagem simplificada via Entity evita problemas de projeção em native query
        Pageable pageableMapeado = validarEMapearPageable(pageable);
        Page<Paciente> pacientes = pacienteRepository.findAllSemRelacionamentos(Objects.requireNonNull(pageableMapeado, "pageable"));
        return pacientes.map(responseBuilder::buildSimplificado);
    }

    private Pageable validarEMapearPageable(Pageable pageable) {
        if (pageable == null || pageable.getSort().isUnsorted()) {
            return pageable;
        }
        
        List<Sort.Order> orders = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String property = order.getProperty();
            String mappedProperty = mapearCampoOrdenacao(property);
            
            if (mappedProperty != null) {
                orders.add(new Sort.Order(order.getDirection(), mappedProperty));
            } else {
                log.warn("Campo de ordenação inválido ignorado: {}", property);
            }
        }
        
        if (orders.isEmpty()) {
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        }
        
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }
    
    private String mapearCampoOrdenacao(String campo) {
        if (campo == null) {
            return null;
        }
        
        String campoLower = campo.toLowerCase();
        
        // Mapear campos comuns para os campos reais da entidade
        switch (campoLower) {
            case "nome":
                return "nomeCompleto";
            case "nomecompleto":
            case "nome_completo":
                return "nomeCompleto";
            case "datanascimento":
            case "data_nascimento":
                return "dataNascimento";
            case "status":
            case "statuspaciente":
            case "status_paciente":
                return "statusPaciente";
            case "createdat":
            case "created_at":
            case "criadoem":
            case "criado_em":
                return "createdAt";
            case "updatedat":
            case "updated_at":
            case "atualizadoem":
            case "atualizado_em":
                return "updatedAt";
            default:
                // Se o campo já está correto (camelCase), retorna como está
                return campo;
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_PACIENTES, keyGenerator = "pacienteCacheKeyGenerator")
    public PacienteResponse atualizar(UUID id, PacienteRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Paciente paciente = pacienteUpdater.atualizar(id, request, tenantId);
            associacoesManager.processarTodas(paciente, request, tenantId);
            return responseBuilder.build(paciente);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao atualizar Paciente. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar Paciente. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao atualizar Paciente", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PACIENTES, keyGenerator = "pacienteCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Paciente paciente = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(paciente);
            pacienteRepository.delete(Objects.requireNonNull(paciente));
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir Paciente. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir Paciente. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir Paciente", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_PACIENTES, keyGenerator = "pacienteCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao inativar Paciente. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar Paciente. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar Paciente", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        Paciente paciente = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(paciente);
        paciente.setActive(false);
        pacienteRepository.save(Objects.requireNonNull(paciente));
    }
}
