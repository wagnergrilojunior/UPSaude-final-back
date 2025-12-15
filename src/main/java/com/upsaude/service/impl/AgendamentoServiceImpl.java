package com.upsaude.service.impl;

import com.upsaude.api.request.AgendamentoRequest;
import com.upsaude.api.response.AgendamentoResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.Agendamento;
import com.upsaude.enums.StatusAgendamentoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.AgendamentoRepository;
import com.upsaude.service.AgendamentoService;
import com.upsaude.service.TenantService;
import com.upsaude.service.support.agendamento.AgendamentoCreator;
import com.upsaude.service.support.agendamento.AgendamentoDomainService;
import com.upsaude.service.support.agendamento.AgendamentoResponseBuilder;
import com.upsaude.service.support.agendamento.AgendamentoStatusManager;
import com.upsaude.service.support.agendamento.AgendamentoTenantEnforcer;
import com.upsaude.service.support.agendamento.AgendamentoUpdater;
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

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final TenantService tenantService;
    private final CacheManager cacheManager;

    private final AgendamentoCreator creator;
    private final AgendamentoUpdater updater;
    private final AgendamentoStatusManager statusManager;
    private final AgendamentoTenantEnforcer tenantEnforcer;
    private final AgendamentoDomainService domainService;
    private final AgendamentoResponseBuilder responseBuilder;

    @Override
    @Transactional
    public AgendamentoResponse criar(AgendamentoRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Agendamento agendamento = creator.criar(request, tenantId);
            AgendamentoResponse response = responseBuilder.build(agendamento);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_AGENDAMENTOS);
            if (cache != null) {
                Object key = Objects.requireNonNull((Object) CacheKeyUtil.agendamento(tenantId, agendamento.getId()));
                cache.put(key, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar agendamento. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir agendamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar agendamento. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_AGENDAMENTOS, keyGenerator = "agendamentoCacheKeyGenerator")
    public AgendamentoResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        if (id == null) {
            throw new BadRequestException("ID do agendamento é obrigatório");
        }
        Agendamento agendamento = tenantEnforcer.validarAcessoCompleto(id, tenantId);
        return responseBuilder.build(agendamento);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return agendamentoRepository.findAllByTenant(tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        return agendamentoRepository.findByPacienteIdAndTenantIdOrderByDataHoraDesc(pacienteId, tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        return agendamentoRepository.findByProfissionalIdAndTenantIdOrderByDataHoraAsc(profissionalId, tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorMedico(UUID medicoId, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        if (medicoId == null) {
            throw new BadRequestException("ID do médico é obrigatório");
        }
        return agendamentoRepository.findByMedicoIdAndTenantIdOrderByDataHoraAsc(medicoId, tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        return agendamentoRepository.findByEstabelecimentoIdAndTenantIdOrderByDataHoraAsc(estabelecimentoId, tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorStatus(StatusAgendamentoEnum status, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        if (status == null) {
            throw new BadRequestException("Status é obrigatório");
        }
        return agendamentoRepository.findByStatusAndTenantIdOrderByDataHoraAsc(status, tenantId, pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorProfissionalEPeriodo(UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        if (dataInicio == null || dataFim == null) {
            throw new BadRequestException("Data de início e data fim são obrigatórias");
        }
        return agendamentoRepository.findByProfissionalIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(profissionalId, dataInicio, dataFim, tenantId, pageable)
                .map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorEstabelecimentoEPeriodo(UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        if (dataInicio == null || dataFim == null) {
            throw new BadRequestException("Data de início e data fim são obrigatórias");
        }
        return agendamentoRepository.findByEstabelecimentoIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(estabelecimentoId, dataInicio, dataFim, tenantId, pageable)
                .map(responseBuilder::build);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listarPorProfissionalEStatus(UUID profissionalId, StatusAgendamentoEnum status, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        if (status == null) {
            throw new BadRequestException("Status é obrigatório");
        }
        return agendamentoRepository.findByProfissionalIdAndStatusAndTenantIdOrderByDataHoraAsc(profissionalId, status, tenantId, pageable)
                .map(responseBuilder::build);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_AGENDAMENTOS, keyGenerator = "agendamentoCacheKeyGenerator")
    public AgendamentoResponse atualizar(UUID id, AgendamentoRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            if (id == null) {
                throw new BadRequestException("ID do agendamento é obrigatório");
            }
            if (request == null) {
                throw new BadRequestException("Dados do agendamento são obrigatórios");
            }
            Agendamento agendamento = updater.atualizar(id, request, tenantId);
            return responseBuilder.build(agendamento);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar agendamento. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar agendamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar agendamento. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_AGENDAMENTOS, keyGenerator = "agendamentoCacheKeyGenerator")
    public AgendamentoResponse cancelar(UUID id, String motivoCancelamento) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            if (id == null) {
                throw new BadRequestException("ID do agendamento é obrigatório");
            }
            Agendamento cancelado = statusManager.cancelar(id, motivoCancelamento, tenantId);
            return responseBuilder.build(cancelado);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao cancelar agendamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao cancelar agendamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao cancelar agendamento. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CachePut(cacheNames = CacheKeyUtil.CACHE_AGENDAMENTOS, keyGenerator = "agendamentoCacheKeyGenerator")
    public AgendamentoResponse confirmar(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            if (id == null) {
                throw new BadRequestException("ID do agendamento é obrigatório");
            }
            Agendamento confirmado = statusManager.confirmar(id, tenantId);
            return responseBuilder.build(confirmado);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao confirmar agendamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao confirmar agendamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao confirmar agendamento. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public AgendamentoResponse reagendar(UUID id, AgendamentoRequest novoAgendamentoRequest, String motivoReagendamento) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            if (id == null) {
                throw new BadRequestException("ID do agendamento é obrigatório");
            }
            if (novoAgendamentoRequest == null) {
                throw new BadRequestException("Dados do novo agendamento são obrigatórios");
            }

            Agendamento novo = statusManager.reagendar(id, novoAgendamentoRequest, motivoReagendamento, tenantId);
            AgendamentoResponse response = responseBuilder.build(novo);

            Cache cache = cacheManager.getCache(CacheKeyUtil.CACHE_AGENDAMENTOS);
            if (cache != null) {
                Object originalKey = Objects.requireNonNull((Object) CacheKeyUtil.agendamento(tenantId, id));
                cache.evict(originalKey);

                Object newKey = Objects.requireNonNull((Object) CacheKeyUtil.agendamento(tenantId, novo.getId()));
                cache.put(newKey, response);
            }

            return response;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao reagendar agendamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao reagendar agendamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao reagendar agendamento. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_AGENDAMENTOS, keyGenerator = "agendamentoCacheKeyGenerator", beforeInvocation = false)
    public void excluir(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            inativarInternal(id, tenantId);
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir(inativar) agendamento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir agendamento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir(inativar) agendamento. ID: {}", id, e);
            throw e;
        }
    }

    private void inativarInternal(UUID id, UUID tenantId) {
        if (id == null) {
            throw new BadRequestException("ID do agendamento é obrigatório");
        }

        Agendamento agendamento = tenantEnforcer.validarAcesso(id, tenantId);
        domainService.validarPodeInativar(agendamento);

        agendamento.setActive(false);
        agendamentoRepository.save(Objects.requireNonNull(agendamento));
        log.info("Agendamento inativado. ID: {}, tenant: {}", id, tenantId);
    }
}
