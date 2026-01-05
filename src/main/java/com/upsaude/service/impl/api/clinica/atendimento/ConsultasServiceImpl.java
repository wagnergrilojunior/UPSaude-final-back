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
import com.upsaude.api.request.clinica.atendimento.ConsultasRequest;
import com.upsaude.api.response.clinica.atendimento.ConsultasResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.clinica.atendimento.Consultas;
import com.upsaude.entity.clinica.prontuario.Prontuarios;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.StatusConsultaEnum;
import com.upsaude.mapper.embeddable.AnamneseConsultaMapper;
import com.upsaude.mapper.embeddable.AtestadoConsultaMapper;
import com.upsaude.mapper.embeddable.DiagnosticoConsultaMapper;
import com.upsaude.mapper.embeddable.EncaminhamentoConsultaMapper;
import com.upsaude.mapper.embeddable.ExamesSolicitadosConsultaMapper;
import com.upsaude.mapper.embeddable.PrescricaoConsultaMapper;
import com.upsaude.repository.clinica.prontuario.ProntuariosRepository;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.repository.clinica.atendimento.ConsultasRepository;
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
    private final ProntuariosRepository prontuariosRepository;
    private final CacheManager cacheManager;
    private final TenantService tenantService;
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
    public ConsultasResponse criar(ConsultaCreateRequest request) {
        ConsultasRequest consultasRequest = new ConsultasRequest();
        consultasRequest.setPaciente(request.getPacienteId());
        consultasRequest.setMedico(request.getMedicoId());
        consultasRequest.setProfissionalSaude(request.getProfissionalSaudeId());
        consultasRequest.setConvenio(request.getConvenioId());
        if (request.getTipoConsulta() != null || request.getMotivo() != null || request.getLocal() != null) {
            com.upsaude.api.request.embeddable.InformacoesConsultaRequest info = new com.upsaude.api.request.embeddable.InformacoesConsultaRequest();
            info.setMotivo(request.getMotivo());
            info.setLocalAtendimento(request.getLocal());
            info.setDataConsulta(java.time.OffsetDateTime.now());
            consultasRequest.setInformacoes(info);
        }
        return criarInternal(consultasRequest);
    }

    @Transactional
    public ConsultasResponse criarInternal(ConsultasRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Consultas saved = creator.criar(request, tenantId, tenant);
            ConsultasResponse response = responseBuilder.build(saved);

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
    public ConsultasResponse obterPorId(UUID id) {
        if (id == null) {
            throw new BadRequestException("ID da consulta é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Consultas entity = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultasResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Consultas> page = repository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultasResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Consultas> page = repository.findByEstabelecimentoIdAndTenantIdOrderByInformacoesDataConsultaDesc(estabelecimentoId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultasResponse atualizar(UUID id, ConsultasRequest request) {
        if (id == null) {
            throw new BadRequestException("ID da consulta é obrigatório");
        }
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            validarTenantAutenticadoOrThrow(tenantId, tenant);

            Consultas updated = updater.atualizar(id, request, tenantId, tenant);
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
            Consultas entity = tenantEnforcer.validarAcesso(id, tenantId);
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

        Consultas entity = tenantEnforcer.validarAcesso(id, tenantId);
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
    public Page<ConsultasResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Consultas> page = repository.findByPacienteIdAndTenantIdOrderByInformacoesDataConsultaDesc(pacienteId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultasResponse atualizarAnamnese(UUID id, ConsultaUpdateAnamneseRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Consultas entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (entity.getAnamnese() == null) {
            entity.setAnamnese(new com.upsaude.entity.embeddable.AnamneseConsulta());
        }
        anamneseConsultaMapper.updateFromRequest(request.getAnamnese(), entity.getAnamnese());
        Consultas saved = repository.save(entity);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultasResponse atualizarDiagnostico(UUID id, ConsultaUpdateDiagnosticoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Consultas entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (entity.getDiagnostico() == null) {
            entity.setDiagnostico(new com.upsaude.entity.embeddable.DiagnosticoConsulta());
        }
        diagnosticoConsultaMapper.updateFromRequest(request.getDiagnostico(), entity.getDiagnostico());
        Consultas saved = repository.save(entity);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultasResponse atualizarPrescricao(UUID id, ConsultaUpdatePrescricaoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Consultas entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (entity.getPrescricao() == null) {
            entity.setPrescricao(new com.upsaude.entity.embeddable.PrescricaoConsulta());
        }
        prescricaoConsultaMapper.updateFromRequest(request.getPrescricao(), entity.getPrescricao());
        Consultas saved = repository.save(entity);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultasResponse atualizarExames(UUID id, ConsultaUpdateExamesRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Consultas entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (entity.getExamesSolicitados() == null) {
            entity.setExamesSolicitados(new com.upsaude.entity.embeddable.ExamesSolicitadosConsulta());
        }
        examesSolicitadosConsultaMapper.updateFromRequest(request.getExames(), entity.getExamesSolicitados());
        Consultas saved = repository.save(entity);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultasResponse atualizarEncaminhamento(UUID id, ConsultaUpdateEncaminhamentoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Consultas entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (entity.getEncaminhamento() == null) {
            entity.setEncaminhamento(new com.upsaude.entity.embeddable.EncaminhamentoConsulta());
        }
        encaminhamentoConsultaMapper.updateFromRequest(request.getEncaminhamento(), entity.getEncaminhamento());
        Consultas saved = repository.save(entity);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultasResponse atualizarAtestado(UUID id, ConsultaUpdateAtestadoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Consultas entity = tenantEnforcer.validarAcesso(id, tenantId);
        if (entity.getAtestado() == null) {
            entity.setAtestado(new com.upsaude.entity.embeddable.AtestadoConsulta());
        }
        atestadoConsultaMapper.updateFromRequest(request.getAtestado(), entity.getAtestado());
        Consultas saved = repository.save(entity);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_CONSULTAS, keyGenerator = "consultasCacheKeyGenerator")
    public ConsultasResponse encerrar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        Consultas entity = tenantEnforcer.validarAcesso(id, tenantId);

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

        Consultas saved = repository.save(entity);
        criarRegistroProntuario(saved, tenant);
        return responseBuilder.build(saved);
    }

    private void criarRegistroProntuario(Consultas consulta, Tenant tenant) {
        Prontuarios prontuario = new Prontuarios();
        prontuario.setPaciente(consulta.getPaciente());
        prontuario.setConsulta(consulta);
        prontuario.setTenant(tenant);
        prontuario.setEstabelecimento(consulta.getEstabelecimento());
        prontuario.setTipoRegistro("CONSULTA");
        prontuario.setTipoRegistroEnum("CONSULTA");
        prontuario.setDataRegistro(java.time.OffsetDateTime.now());
        prontuario.setActive(true);

        StringBuilder resumo = new StringBuilder();
        resumo.append("Consulta encerrada");
        if (consulta.getInformacoes() != null && consulta.getInformacoes().getMotivo() != null) {
            resumo.append(" - Motivo: ").append(consulta.getInformacoes().getMotivo());
        }
        if (consulta.getMedico() != null && consulta.getMedico().getDadosPessoaisBasicos() != null) {
            resumo.append(" - Médico: ").append(consulta.getMedico().getDadosPessoaisBasicos().getNomeCompleto());
        }
        prontuario.setResumo(resumo.toString());

        prontuariosRepository.save(Objects.requireNonNull(prontuario));
        log.debug("Registro de prontuário criado para consulta. ID: {}", consulta.getId());
    }

}
