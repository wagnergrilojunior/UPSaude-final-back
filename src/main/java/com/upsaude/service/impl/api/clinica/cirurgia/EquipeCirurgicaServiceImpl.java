package com.upsaude.service.impl.api.clinica.cirurgia;

import com.upsaude.api.request.clinica.cirurgia.EquipeCirurgicaRequest;
import com.upsaude.api.response.clinica.cirurgia.EquipeCirurgicaResponse;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgica;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.cirurgia.EquipeCirurgicaRepository;
import com.upsaude.service.api.clinica.cirurgia.EquipeCirurgicaService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.cirurgia.EquipeCirurgicaCreator;
import com.upsaude.service.api.support.cirurgia.EquipeCirurgicaResponseBuilder;
import com.upsaude.service.api.support.cirurgia.EquipeCirurgicaTenantEnforcer;
import com.upsaude.service.api.support.cirurgia.EquipeCirurgicaUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class EquipeCirurgicaServiceImpl implements EquipeCirurgicaService {

    private final EquipeCirurgicaRepository repository;
    private final TenantService tenantService;

    private final EquipeCirurgicaCreator creator;
    private final EquipeCirurgicaUpdater updater;
    private final EquipeCirurgicaTenantEnforcer tenantEnforcer;
    private final EquipeCirurgicaResponseBuilder responseBuilder;

    @Override
    @Transactional
    public EquipeCirurgicaResponse criar(EquipeCirurgicaRequest request) {
        log.debug("Criando equipe cirúrgica. Request: {}", request);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            EquipeCirurgica saved = creator.criar(request, tenantId, tenant);
            return responseBuilder.build(saved);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar equipe cirúrgica. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar equipe cirúrgica. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir equipe cirúrgica", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar equipe cirúrgica. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public EquipeCirurgicaResponse obterPorId(UUID id) {
        log.debug("Buscando equipe cirúrgica por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da equipe cirúrgica é obrigatório");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            EquipeCirurgica entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
            return responseBuilder.build(entity);
        } catch (NotFoundException e) {
            log.warn("Equipe cirúrgica não encontrada. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar equipe cirúrgica. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar equipe cirúrgica", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar equipe cirúrgica. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipeCirurgicaResponse> listar(Pageable pageable) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);
            return repository.findByTenantOrderByCreatedAtDesc(tenant, pageable)
                .map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar equipes cirúrgicas. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar equipes cirúrgicas", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar equipes cirúrgicas. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipeCirurgicaResponse> listarPorCirurgia(UUID cirurgiaId, Pageable pageable) {
        if (cirurgiaId == null) {
            throw new BadRequestException("ID da cirurgia é obrigatório");
        }
        try {
            return repository.findByCirurgiaIdOrderByCreatedAtAsc(cirurgiaId, pageable)
                .map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar equipes cirúrgicas por cirurgia. CirurgiaId: {}, Pageable: {}", cirurgiaId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar equipes cirúrgicas por cirurgia", e);
        }
    }

    @Override
    @Transactional
    public EquipeCirurgicaResponse atualizar(UUID id, EquipeCirurgicaRequest request) {
        log.debug("Atualizando equipe cirúrgica. ID: {}, Request: {}", id, request);

        if (id == null) {
            throw new BadRequestException("ID da equipe cirúrgica é obrigatório");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = obterTenantAutenticadoOrThrow(tenantId);

            EquipeCirurgica updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao atualizar equipe cirúrgica. ID: {}, erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar equipe cirúrgica. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar equipe cirúrgica", e);
        }
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo equipe cirúrgica permanentemente. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            EquipeCirurgica entity = tenantEnforcer.validarAcesso(id, tenantId);
            repository.delete(Objects.requireNonNull(entity));
            log.info("Equipe cirúrgica excluída permanentemente com sucesso. ID: {}", id);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir equipe cirúrgica. ID: {}, erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir equipe cirúrgica. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir equipe cirúrgica", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        log.debug("Inativando equipe cirúrgica. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao inativar equipe cirúrgica. ID: {}, erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar equipe cirúrgica. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao inativar equipe cirúrgica", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da equipe cirúrgica é obrigatório");
        }
        EquipeCirurgica entity = tenantEnforcer.validarAcesso(id, tenantId);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Equipe cirúrgica inativada com sucesso. ID: {}", id);
    }

    private Tenant obterTenantAutenticadoOrThrow(UUID tenantId) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
        return tenant;
    }
}

