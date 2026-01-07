package com.upsaude.service.impl.api.agendamento;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.agendamento.AgendamentoRequest;
import com.upsaude.api.response.agendamento.AgendamentoResponse;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.service.api.agendamento.AgendamentoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.agendamento.AgendamentoCreator;
import com.upsaude.service.api.support.agendamento.AgendamentoDomainService;
import com.upsaude.service.api.support.agendamento.AgendamentoResponseBuilder;
import com.upsaude.service.api.support.agendamento.AgendamentoTenantEnforcer;
import com.upsaude.service.api.support.agendamento.AgendamentoUpdater;
import com.upsaude.service.api.support.agendamento.AgendamentoValidationService;
import org.springframework.dao.DataAccessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService {

    private final AgendamentoRepository repository;
    private final TenantService tenantService;

    private final AgendamentoTenantEnforcer tenantEnforcer;
    private final AgendamentoCreator creator;
    private final AgendamentoUpdater updater;
    private final AgendamentoResponseBuilder responseBuilder;
    private final AgendamentoDomainService domainService;

    @Override
    @Transactional
    public AgendamentoResponse criar(AgendamentoRequest request) {
        log.debug("Criando novo agendamento. Request: {}", request);
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        Agendamento saved = creator.criar(request, tenantId, tenant);
        AgendamentoResponse response = responseBuilder.build(saved);
        log.info("Agendamento criado com sucesso. ID: {}", response.getId());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public AgendamentoResponse obterPorId(UUID id) {
        log.debug("Buscando agendamento por ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        Agendamento entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listar(Pageable pageable) {
        log.debug("Listando agendamentos. Pageable: {}", pageable);
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Agendamento> entities = repository.findAllByTenant(tenantId, pageable);
        return entities.map(responseBuilder::build);
    }

    @Override
    @Transactional
    public AgendamentoResponse atualizar(UUID id, AgendamentoRequest request) {
        log.debug("Atualizando agendamento. ID: {}, Request: {}", id, request);
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        Agendamento updated = updater.atualizar(id, request, tenantId, tenant);
        AgendamentoResponse response = responseBuilder.build(updated);
        log.info("Agendamento atualizado com sucesso. ID: {}", response.getId());
        return response;
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo agendamento. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            Agendamento entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(entity);
            log.info("Agendamento excluído com sucesso. ID: {}", id);
        } catch (com.upsaude.exception.BadRequestException e) {
            log.warn("Erro de validação ao excluir Agendamento. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir Agendamento. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir Agendamento", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        log.debug("Inativando agendamento. ID: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            Agendamento entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeInativar(entity);
            entity.setActive(false);
            repository.save(entity);
            log.info("Agendamento inativado com sucesso. ID: {}", id);
        } catch (com.upsaude.exception.BadRequestException e) {
            log.warn("Erro de validação ao inativar Agendamento. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar Agendamento. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar Agendamento", e);
        }
    }
}

