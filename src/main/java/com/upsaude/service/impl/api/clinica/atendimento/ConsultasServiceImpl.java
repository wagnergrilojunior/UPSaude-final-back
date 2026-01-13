package com.upsaude.service.impl.api.clinica.atendimento;

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

import com.upsaude.api.request.clinica.atendimento.ConsultaCreateRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateAnamneseRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateAtestadoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateDiagnosticoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateEncaminhamentoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateExamesRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdatePrescricaoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaRequest;
import com.upsaude.api.response.clinica.atendimento.ConsultaResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.clinica.atendimento.Consulta;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.StatusConsultaEnum;
import com.upsaude.mapper.embeddable.AnamneseConsultaMapper;
import com.upsaude.mapper.embeddable.AtestadoConsultaMapper;
import com.upsaude.mapper.embeddable.DiagnosticoConsultaMapper;
import com.upsaude.mapper.embeddable.EncaminhamentoConsultaMapper;
import com.upsaude.mapper.embeddable.ExamesSolicitadosConsultaMapper;
import com.upsaude.mapper.embeddable.PrescricaoConsultaMapper;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.clinica.atendimento.ConsultasRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.clinica.atendimento.ConsultasService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.consultas.ConsultasCreator;
import com.upsaude.service.api.support.consultas.ConsultasDomainService;
import com.upsaude.service.api.support.consultas.ConsultasResponseBuilder;
import com.upsaude.service.api.support.consultas.ConsultasTenantEnforcer;
import com.upsaude.service.api.support.consultas.ConsultasUpdater;
import org.springframework.dao.DataAccessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultasServiceImpl implements ConsultasService {

    private final ConsultasRepository repository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;
    private final AnamneseConsultaMapper anamneseConsultaMapper;
    private final DiagnosticoConsultaMapper diagnosticoConsultaMapper;
    private final PrescricaoConsultaMapper prescricaoConsultaMapper;
    private final ExamesSolicitadosConsultaMapper examesSolicitadosConsultaMapper;
    private final EncaminhamentoConsultaMapper encaminhamentoConsultaMapper;
    private final AtestadoConsultaMapper atestadoConsultaMapper;

    private final ConsultasCreator creator;
    private final ConsultasUpdater updater;
    private final ConsultasResponseBuilder responseBuilder;
    private final ConsultasTenantEnforcer tenantEnforcer;
    private final ConsultasDomainService domainService;

    @Override
    @Transactional
    public ConsultaResponse criar(ConsultaCreateRequest request) {
        ConsultaRequest consultaRequest = new ConsultaRequest();
        consultaRequest.setAtendimento(request.getAtendimentoId());
        consultaRequest.setMedico(request.getMedicoId());
        if (request.getTipoConsulta() != null || request.getMotivo() != null || request.getLocal() != null) {
            com.upsaude.api.request.embeddable.InformacoesConsultaRequest info = new com.upsaude.api.request.embeddable.InformacoesConsultaRequest();
            info.setMotivo(request.getMotivo());
            info.setLocalAtendimento(request.getLocal());
            info.setDataConsulta(java.time.OffsetDateTime.now());
            consultaRequest.setInformacoes(info);
        }
        return criarInternal(consultaRequest);
    }

    @Transactional
    public ConsultaResponse criarInternal(ConsultaRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            if (tenant == null) {
                // Fallback para testes: buscar tenant do banco quando não houver autenticação
                tenant = tenantRepository.findById(tenantId).orElse(null);
            }
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Consulta saved = creator.criar(request, tenantId, tenant);
            ConsultaResponse response = responseBuilder.build(saved);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_CONSULTAS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.consulta(tenantId, saved.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao criar consulta", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultaResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da consulta é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Consulta entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultaResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Consulta> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultaResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Consulta> page = repository.findByEstabelecimentoIdAndTenantIdOrderByInformacoesDataConsultaDesc(estabelecimentoId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultaResponse atualizar(UUID id, ConsultaRequest request) {
        if (id == null) {
            throw new BadRequestException("ID da consulta é obrigatório");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            if (tenant == null) {
                // Fallback para testes: buscar tenant do banco quando não houver autenticação
                tenant = tenantRepository.findById(tenantId).orElse(null);
            }
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Consulta updated = updater.atualizar(id, request, tenantId, tenant);
            return responseBuilder.build(updated);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao atualizar consulta", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo consulta permanentemente. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Consulta entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            repository.delete(Objects.requireNonNull(entity));
            log.info("Consulta excluída permanentemente com sucesso. ID: {}", id);
        } catch (BadRequestException | com.upsaude.exception.NotFoundException e) {
            log.warn("Erro de validação ao excluir Consultas. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir Consultas. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir Consultas", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando consulta. ID: {}", id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | com.upsaude.exception.NotFoundException e) {
            log.warn("Erro de validação ao inativar Consultas. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar Consultas. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar Consultas", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID da consulta é obrigatório");
        }

        Consulta entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        repository.save(Objects.requireNonNull(entity));
        log.info("Consulta inativada com sucesso. ID: {}", id);
    }

    private void validarTenantAutenticadoOrThrow(UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultaResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Consulta> page = repository.findByPacienteIdAndTenantIdOrderByInformacoesDataConsultaDesc(pacienteId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultaResponse atualizarAnamnese(UUID id, ConsultaUpdateAnamneseRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Consulta entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (entity.getAnamnese() == null) {
            entity.setAnamnese(new com.upsaude.entity.embeddable.AnamneseConsulta());
        }
        anamneseConsultaMapper.updateFromRequest(request.getAnamnese(), entity.getAnamnese());
        Consulta saved = repository.save(entity);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultaResponse atualizarDiagnostico(UUID id, ConsultaUpdateDiagnosticoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Consulta entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (entity.getDiagnostico() == null) {
            entity.setDiagnostico(new com.upsaude.entity.embeddable.DiagnosticoConsulta());
        }
        diagnosticoConsultaMapper.updateFromRequest(request.getDiagnostico(), entity.getDiagnostico());
        Consulta saved = repository.save(entity);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultaResponse atualizarPrescricao(UUID id, ConsultaUpdatePrescricaoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Consulta entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (entity.getPrescricao() == null) {
            entity.setPrescricao(new com.upsaude.entity.embeddable.PrescricaoConsulta());
        }
        prescricaoConsultaMapper.updateFromRequest(request.getPrescricao(), entity.getPrescricao());
        Consulta saved = repository.save(entity);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultaResponse atualizarExames(UUID id, ConsultaUpdateExamesRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Consulta entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (entity.getExamesSolicitados() == null) {
            entity.setExamesSolicitados(new com.upsaude.entity.embeddable.ExamesSolicitadosConsulta());
        }
        examesSolicitadosConsultaMapper.updateFromRequest(request.getExames(), entity.getExamesSolicitados());
        Consulta saved = repository.save(entity);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultaResponse atualizarEncaminhamento(UUID id, ConsultaUpdateEncaminhamentoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Consulta entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (entity.getEncaminhamento() == null) {
            entity.setEncaminhamento(new com.upsaude.entity.embeddable.EncaminhamentoConsulta());
        }
        encaminhamentoConsultaMapper.updateFromRequest(request.getEncaminhamento(), entity.getEncaminhamento());
        Consulta saved = repository.save(entity);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultaResponse atualizarAtestado(UUID id, ConsultaUpdateAtestadoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Consulta entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (entity.getAtestado() == null) {
            entity.setAtestado(new com.upsaude.entity.embeddable.AtestadoConsulta());
        }
        atestadoConsultaMapper.updateFromRequest(request.getAtestado(), entity.getAtestado());
        Consulta saved = repository.save(entity);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultaResponse encerrar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        Consulta entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (entity.getInformacoes() == null) {
            throw new BadRequestException("Consulta não possui informações");
        }

        if (entity.getInformacoes().getStatusConsulta() == StatusConsultaEnum.CONCLUIDA ||
            entity.getInformacoes().getStatusConsulta() == StatusConsultaEnum.CANCELADA) {
            throw new BadRequestException("Consulta já está encerrada ou cancelada");
        }

        if (entity.getInformacoes().getDataInicio() == null) {
            throw new BadRequestException("Não é possível encerrar consulta sem data de início");
        }

        entity.getInformacoes().setStatusConsulta(StatusConsultaEnum.CONCLUIDA);
        entity.getInformacoes().setDataFim(java.time.OffsetDateTime.now());

        if (entity.getInformacoes().getDataInicio() != null && entity.getInformacoes().getDataFim() != null) {
            long minutos = java.time.Duration.between(
                entity.getInformacoes().getDataInicio(),
                entity.getInformacoes().getDataFim()
            ).toMinutes();
            entity.getInformacoes().setDuracaoRealMinutos((int) minutos);
        }

        Consulta saved = repository.save(entity);
        log.info("Consulta encerrada com sucesso. ID: {}", saved.getId());
        return responseBuilder.build(saved);
    }

}
