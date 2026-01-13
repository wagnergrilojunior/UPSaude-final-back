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

import com.upsaude.api.request.clinica.atendimento.AtendimentoCreateRequest;
import com.upsaude.api.request.clinica.atendimento.AtendimentoRequest;
import com.upsaude.api.request.clinica.atendimento.AtendimentoTriagemRequest;
import com.upsaude.api.response.clinica.atendimento.AtendimentoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.clinica.atendimento.SinalVital;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.StatusAtendimentoEnum;
import com.upsaude.enums.TipoAtendimentoEnum;
import com.upsaude.mapper.clinica.atendimento.SinalVitalMapper;
import com.upsaude.mapper.embeddable.AnamneseAtendimentoMapper;
import com.upsaude.mapper.embeddable.ClassificacaoRiscoAtendimentoMapper;
import com.upsaude.mapper.embeddable.DiagnosticoAtendimentoMapper;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.clinica.atendimento.SinalVitalRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.financeiro.FinanceiroIntegrationService;
import com.upsaude.service.api.clinica.atendimento.AtendimentoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.atendimento.AtendimentoCreator;
import com.upsaude.service.api.support.atendimento.AtendimentoDomainService;
import com.upsaude.service.api.support.atendimento.AtendimentoResponseBuilder;
import com.upsaude.service.api.support.atendimento.AtendimentoTenantEnforcer;
import com.upsaude.service.api.support.atendimento.AtendimentoUpdater;
import com.upsaude.service.api.support.atendimento.AtendimentoValidationService;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import org.springframework.dao.DataAccessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtendimentoServiceImpl implements AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;
    private final CacheManager cacheManager;
    private final AnamneseAtendimentoMapper anamneseAtendimentoMapper;
    private final ClassificacaoRiscoAtendimentoMapper classificacaoRiscoAtendimentoMapper;
    private final DiagnosticoAtendimentoMapper diagnosticoAtendimentoMapper;
    private final SinalVitalMapper sinalVitalMapper;
    private final SinalVitalRepository sinalVitalRepository;

    private final AtendimentoValidationService validationService;
    private final AtendimentoTenantEnforcer tenantEnforcer;
    private final AtendimentoCreator creator;
    private final AtendimentoUpdater updater;
    private final AtendimentoResponseBuilder responseBuilder;
    private final AtendimentoDomainService domainService;
    private final FinanceiroIntegrationService financeiroIntegrationService;

    @Override
    @Transactional
    public AtendimentoResponse criar(AtendimentoCreateRequest request) {
        log.debug("Criando novo atendimento. Request: {}", request);
        if (request == null || request.getPacienteId() == null || request.getProfissionalId() == null) {
            throw new BadRequestException("Paciente e profissional são obrigatórios");
        }

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            // Fallback para testes: buscar tenant do banco quando não houver autenticação
            tenant = tenantRepository.findById(Objects.requireNonNull(tenantId)).orElse(null);
        }

        AtendimentoRequest atendimentoRequest = new AtendimentoRequest();
        atendimentoRequest.setPaciente(request.getPacienteId());
        atendimentoRequest.setProfissional(request.getProfissionalId());
        atendimentoRequest.setEquipeSaude(request.getEquipeSaudeId());
        atendimentoRequest.setConvenio(request.getConvenioId());
        if (request.getTipoAtendimento() != null || request.getMotivo() != null
                || request.getLocalAtendimento() != null) {
            com.upsaude.api.request.embeddable.InformacoesAtendimentoRequest info = new com.upsaude.api.request.embeddable.InformacoesAtendimentoRequest();
            if (request.getTipoAtendimento() != null) {
                try {
                    info.setTipoAtendimento(TipoAtendimentoEnum.valueOf(request.getTipoAtendimento()));
                } catch (IllegalArgumentException e) {
                    log.warn("Tipo de atendimento inválido: {}", request.getTipoAtendimento());
                }
            }
            info.setMotivo(request.getMotivo());
            info.setLocalAtendimento(request.getLocalAtendimento());
            info.setDataHora(java.time.OffsetDateTime.now());
            atendimentoRequest.setInformacoes(info);
        }

        Atendimento saved = creator.criar(atendimentoRequest, tenantId, tenant);
        AtendimentoResponse response = responseBuilder.build(saved);

        Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_ATENDIMENTO);
        if (cache != null && response != null && response.getId() != null) {
            cache.put(Objects.requireNonNull(CacheKeyUtil.atendimento(tenantId, response.getId())), response);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_ATENDIMENTO, keyGenerator = "atendimentoCacheKeyGenerator")
    public AtendimentoResponse obterPorId(UUID id) {
        log.debug("Buscando atendimento por ID: {} (cache miss)", id);
        validationService.validarId(id);

        UUID tenantId = tenantService.validarTenantAtual();
        Atendimento atendimento = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(atendimento);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoResponse> listar(Pageable pageable) {
        log.debug("Listando atendimentos paginados. Pageable: {}", pageable);
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Atendimento> page = atendimentoRepository.findAllByTenant(tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        if (pacienteId == null) {
            log.warn("ID de paciente nulo recebido para listagem de atendimentos");
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Atendimento> page = atendimentoRepository
                .findByPacienteIdAndTenantIdOrderByInformacoesDataHoraDesc(pacienteId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        if (profissionalId == null) {
            log.warn("ID de profissional nulo recebido para listagem de atendimentos");
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Atendimento> page = atendimentoRepository
                .findByProfissionalIdAndTenantIdOrderByInformacoesDataHoraDesc(profissionalId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        if (estabelecimentoId == null) {
            log.warn("ID de estabelecimento nulo recebido para listagem de atendimentos");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        UUID tenantId = tenantService.validarTenantAtual();
        Page<Atendimento> page = atendimentoRepository.findByEstabelecimentoIdAndTenantIdOrderByInformacoesDataHoraDesc(
                estabelecimentoId, tenantId, pageable);
        return page.map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_ATENDIMENTO, keyGenerator = "atendimentoCacheKeyGenerator")
    public AtendimentoResponse atualizar(UUID id, AtendimentoRequest request) {
        log.debug("Atualizando atendimento. ID: {}, Request: {}", id, request);
        validationService.validarId(id);
        validationService.validarObrigatorios(request);

        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        Atendimento updated = updater.atualizar(id, request, tenantId, tenant);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_ATENDIMENTO, keyGenerator = "atendimentoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        log.debug("Excluindo atendimento permanentemente. ID: {}", id);
        validationService.validarId(id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Atendimento entity = tenantEnforcer.validarAcesso(id, tenantId);
            domainService.validarPodeDeletar(entity);
            atendimentoRepository.delete(Objects.requireNonNull(entity));
            log.info("Atendimento excluído permanentemente com sucesso. ID: {}, tenant: {}", id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao excluir Atendimento. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir Atendimento. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir Atendimento", e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_ATENDIMENTO, keyGenerator = "atendimentoCacheKeyGenerator", beforeInvocation = false)
    public void inativar(UUID id) {
        log.debug("Inativando atendimento. ID: {}", id);
        validationService.validarId(id);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao inativar Atendimento. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar Atendimento. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar Atendimento", e);
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        Atendimento entity = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(entity);
        entity.setActive(false);
        atendimentoRepository.save(entity);
        log.info("Atendimento inativado com sucesso. ID: {}, tenant: {}", id, tenantId);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_ATENDIMENTO, keyGenerator = "atendimentoCacheKeyGenerator")
    public AtendimentoResponse iniciar(UUID id) {
        log.debug("Iniciando atendimento. ID: {}", id);
        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        Atendimento entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (entity.getInformacoes() == null) {
            entity.setInformacoes(new com.upsaude.entity.embeddable.InformacoesAtendimento());
        }

        if (entity.getInformacoes().getStatusAtendimento() != StatusAtendimentoEnum.AGENDADO &&
                entity.getInformacoes().getStatusAtendimento() != StatusAtendimentoEnum.EM_ESPERA) {
            throw new BadRequestException("Atendimento só pode ser iniciado se estiver agendado ou em espera");
        }

        entity.getInformacoes().setStatusAtendimento(StatusAtendimentoEnum.EM_ANDAMENTO);
        entity.getInformacoes().setDataInicio(java.time.OffsetDateTime.now());

        Atendimento saved = atendimentoRepository.save(entity);
        log.info("Atendimento iniciado com sucesso. ID: {}", id);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_ATENDIMENTO, keyGenerator = "atendimentoCacheKeyGenerator")
    public AtendimentoResponse atualizarTriagem(UUID id, AtendimentoTriagemRequest request) {
        log.debug("Atualizando triagem do atendimento. ID: {}", id);
        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        Atendimento entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (request.getAnamnese() != null) {
            if (entity.getAnamnese() == null) {
                entity.setAnamnese(new com.upsaude.entity.embeddable.AnamneseAtendimento());
            }
            anamneseAtendimentoMapper.updateFromRequest(request.getAnamnese(), entity.getAnamnese());

            // Processar Sinais Vitais estruturados
            if (request.getAnamnese().getSinalVitalRecord() != null) {
                SinalVital sinalVital = entity.getAnamnese().getSinalVitalRecord();
                if (sinalVital == null) {
                    sinalVital = sinalVitalMapper.toEntity(request.getAnamnese().getSinalVitalRecord());
                    sinalVital.setPaciente(entity.getPaciente());
                    sinalVital.setAtendimento(entity);
                    sinalVital.setTenant(entity.getTenant());
                    sinalVital.setEstabelecimento(entity.getEstabelecimento());
                    sinalVital.setDataMedicao(java.time.OffsetDateTime.now());
                } else {
                    sinalVitalMapper.updateEntityFromRequest(request.getAnamnese().getSinalVitalRecord(), sinalVital);
                }
                sinalVital = sinalVitalRepository.save(sinalVital);
                entity.getAnamnese().setSinalVitalRecord(sinalVital);
            }
        }

        if (request.getClassificacaoRisco() != null) {
            if (entity.getClassificacaoRisco() == null) {
                entity.setClassificacaoRisco(new com.upsaude.entity.embeddable.ClassificacaoRiscoAtendimento());
            }
            classificacaoRiscoAtendimentoMapper.updateFromRequest(request.getClassificacaoRisco(),
                    entity.getClassificacaoRisco());
        }

        if (request.getDiagnostico() != null) {
            if (entity.getDiagnostico() == null) {
                entity.setDiagnostico(new com.upsaude.entity.embeddable.DiagnosticoAtendimento());
            }
            diagnosticoAtendimentoMapper.updateFromRequest(request.getDiagnostico(), entity.getDiagnostico());
        }

        Atendimento saved = atendimentoRepository.save(Objects.requireNonNull(entity));
        log.info("Triagem do atendimento atualizada com sucesso. ID: {}", id);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_ATENDIMENTO, keyGenerator = "atendimentoCacheKeyGenerator")
    public AtendimentoResponse atualizarClassificacaoRisco(UUID id, AtendimentoTriagemRequest request) {
        log.debug("Atualizando classificação de risco do atendimento. ID: {}", id);
        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        Atendimento entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (request.getClassificacaoRisco() == null) {
            throw new BadRequestException("Dados de classificação de risco são obrigatórios");
        }

        if (entity.getClassificacaoRisco() == null) {
            entity.setClassificacaoRisco(new com.upsaude.entity.embeddable.ClassificacaoRiscoAtendimento());
        }
        classificacaoRiscoAtendimentoMapper.updateFromRequest(request.getClassificacaoRisco(),
                entity.getClassificacaoRisco());

        Atendimento saved = atendimentoRepository.save(entity);
        log.info("Classificação de risco do atendimento atualizada com sucesso. ID: {}", id);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_ATENDIMENTO, keyGenerator = "atendimentoCacheKeyGenerator")
    public AtendimentoResponse encerrar(UUID id) {
        log.debug("Encerrando atendimento. ID: {}", id);
        validationService.validarId(id);
        UUID tenantId = tenantService.validarTenantAtual();
        Atendimento entity = tenantEnforcer.validarAcesso(id, tenantId);

        if (entity.getInformacoes() == null) {
            throw new BadRequestException("Atendimento não possui informações");
        }

        if (entity.getInformacoes().getStatusAtendimento() == StatusAtendimentoEnum.CONCLUIDO ||
                entity.getInformacoes().getStatusAtendimento() == StatusAtendimentoEnum.CANCELADO) {
            throw new BadRequestException("Atendimento já está encerrado ou cancelado");
        }

        if (entity.getInformacoes().getDataInicio() == null) {
            throw new BadRequestException("Não é possível encerrar atendimento sem data de início");
        }

        entity.getInformacoes().setStatusAtendimento(StatusAtendimentoEnum.CONCLUIDO);
        entity.getInformacoes().setDataFim(java.time.OffsetDateTime.now());

        if (entity.getInformacoes().getDataInicio() != null && entity.getInformacoes().getDataFim() != null) {
            long minutos = java.time.Duration.between(
                    entity.getInformacoes().getDataInicio(),
                    entity.getInformacoes().getDataFim()).toMinutes();
            entity.getInformacoes().setDuracaoRealMinutos((int) minutos);
        }

        Atendimento saved = atendimentoRepository.save(entity);

        // Consumir reserva ao realizar/encerrar atendimento (modelo híbrido)
        financeiroIntegrationService.consumirReserva(saved.getId());

        log.info("Atendimento encerrado com sucesso. ID: {}", id);
        return responseBuilder.build(saved);
    }
}
