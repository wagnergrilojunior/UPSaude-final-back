package com.upsaude.service.impl;

import com.upsaude.api.request.ControlePontoRequest;
import com.upsaude.api.response.ControlePontoResponse;
import com.upsaude.entity.ControlePonto;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ControlePontoMapper;
import com.upsaude.repository.ControlePontoRepository;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.MedicosRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.service.ControlePontoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de ControlePonto.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ControlePontoServiceImpl implements ControlePontoService {

    private final ControlePontoRepository controlePontoRepository;
    private final ControlePontoMapper controlePontoMapper;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final MedicosRepository medicosRepository;

    @Override
    @Transactional
    @CacheEvict(value = "controleponto", allEntries = true)
    public ControlePontoResponse criar(ControlePontoRequest request) {
        log.debug("Criando novo registro de ponto");

        validarDadosBasicos(request);

        ControlePonto controlePonto = controlePontoMapper.fromRequest(request);

        // Carrega e define o profissional (obrigatório se médico não for fornecido)
        if (request.getProfissional() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissional())
                    .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + request.getProfissional()));
            controlePonto.setProfissional(profissional);
        }

        // Carrega e define o médico (opcional)
        if (request.getMedico() != null) {
            Medicos medico = medicosRepository.findById(request.getMedico())
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedico()));
            controlePonto.setMedico(medico);
        }

        controlePonto.setActive(true);

        ControlePonto controlePontoSalvo = controlePontoRepository.save(controlePonto);
        log.info("Registro de ponto criado com sucesso. ID: {}", controlePontoSalvo.getId());

        return controlePontoMapper.toResponse(controlePontoSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "controleponto", key = "#id")
    public ControlePontoResponse obterPorId(UUID id) {
        log.debug("Buscando registro de ponto por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do registro de ponto é obrigatório");
        }

        ControlePonto controlePonto = controlePontoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro de ponto não encontrado com ID: " + id));

        return controlePontoMapper.toResponse(controlePonto);
    }

    @Override
    public Page<ControlePontoResponse> listar(Pageable pageable) {
        log.debug("Listando registros de ponto paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<ControlePonto> registros = controlePontoRepository.findAll(pageable);
        return registros.map(controlePontoMapper::toResponse);
    }

    @Override
    public Page<ControlePontoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        log.debug("Listando registros de ponto do profissional: {}. Página: {}, Tamanho: {}",
                profissionalId, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }

        Page<ControlePonto> registros = controlePontoRepository.findByProfissionalIdOrderByDataHoraDesc(profissionalId, pageable);
        return registros.map(controlePontoMapper::toResponse);
    }

    @Override
    public Page<ControlePontoResponse> listarPorMedico(UUID medicoId, Pageable pageable) {
        log.debug("Listando registros de ponto do médico: {}. Página: {}, Tamanho: {}",
                medicoId, pageable.getPageNumber(), pageable.getPageSize());

        if (medicoId == null) {
            throw new BadRequestException("ID do médico é obrigatório");
        }

        Page<ControlePonto> registros = controlePontoRepository.findByMedicoIdOrderByDataHoraDesc(medicoId, pageable);
        return registros.map(controlePontoMapper::toResponse);
    }

    @Override
    public Page<ControlePontoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando registros de ponto do estabelecimento: {}. Página: {}, Tamanho: {}",
                estabelecimentoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Page<ControlePonto> registros = controlePontoRepository.findByEstabelecimentoIdOrderByDataHoraDesc(estabelecimentoId, pageable);
        return registros.map(controlePontoMapper::toResponse);
    }

    @Override
    public Page<ControlePontoResponse> listarPorProfissionalEData(UUID profissionalId, LocalDate data, Pageable pageable) {
        log.debug("Listando registros de ponto do profissional: {} na data: {}. Página: {}, Tamanho: {}",
                profissionalId, data, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        if (data == null) {
            throw new BadRequestException("Data é obrigatória");
        }

        Page<ControlePonto> registros = controlePontoRepository.findByProfissionalIdAndDataPontoOrderByDataHoraAsc(profissionalId, data, pageable);
        return registros.map(controlePontoMapper::toResponse);
    }

    @Override
    public Page<ControlePontoResponse> listarPorProfissionalEPeriodo(UUID profissionalId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
        log.debug("Listando registros de ponto do profissional: {} no período: {} a {}. Página: {}, Tamanho: {}",
                profissionalId, dataInicio, dataFim, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        if (dataInicio == null || dataFim == null) {
            throw new BadRequestException("Data de início e data fim são obrigatórias");
        }

        Page<ControlePonto> registros = controlePontoRepository.findByProfissionalIdAndDataPontoBetweenOrderByDataHoraAsc(profissionalId, dataInicio, dataFim, pageable);
        return registros.map(controlePontoMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "controleponto", key = "#id")
    public ControlePontoResponse atualizar(UUID id, ControlePontoRequest request) {
        log.debug("Atualizando registro de ponto. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do registro de ponto é obrigatório");
        }

        validarDadosBasicos(request);

        ControlePonto controlePontoExistente = controlePontoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro de ponto não encontrado com ID: " + id));

        atualizarDadosPonto(controlePontoExistente, request);

        ControlePonto controlePontoAtualizado = controlePontoRepository.save(controlePontoExistente);
        log.info("Registro de ponto atualizado com sucesso. ID: {}", controlePontoAtualizado.getId());

        return controlePontoMapper.toResponse(controlePontoAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "controleponto", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo registro de ponto. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do registro de ponto é obrigatório");
        }

        ControlePonto controlePonto = controlePontoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro de ponto não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(controlePonto.getActive())) {
            throw new BadRequestException("Registro de ponto já está inativo");
        }

        controlePonto.setActive(false);
        controlePontoRepository.save(controlePonto);
        log.info("Registro de ponto excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(ControlePontoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do registro de ponto são obrigatórios");
        }
        if (request.getDataHora() == null) {
            throw new BadRequestException("Data e hora do ponto são obrigatórias");
        }
        if (request.getDataPonto() == null) {
            throw new BadRequestException("Data do ponto é obrigatória");
        }
        if (request.getTipoPonto() == null) {
            throw new BadRequestException("Tipo de ponto é obrigatório");
        }
        if (request.getProfissional() == null && request.getMedico() == null) {
            throw new BadRequestException("ID do profissional ou médico é obrigatório");
        }
    }

    private void atualizarDadosPonto(ControlePonto controlePonto, ControlePontoRequest request) {
        if (request.getDataHora() != null) {
            controlePonto.setDataHora(request.getDataHora());
        }
        if (request.getDataPonto() != null) {
            controlePonto.setDataPonto(request.getDataPonto());
        }
        if (request.getTipoPonto() != null) {
            controlePonto.setTipoPonto(request.getTipoPonto());
        }
        if (request.getLatitude() != null) {
            controlePonto.setLatitude(request.getLatitude());
        }
        if (request.getLongitude() != null) {
            controlePonto.setLongitude(request.getLongitude());
        }
        if (request.getEnderecoIp() != null) {
            controlePonto.setEnderecoIp(request.getEnderecoIp());
        }
        if (request.getObservacoes() != null) {
            controlePonto.setObservacoes(request.getObservacoes());
        }
        if (request.getJustificativa() != null) {
            controlePonto.setJustificativa(request.getJustificativa());
        }
        if (request.getAprovado() != null) {
            controlePonto.setAprovado(request.getAprovado());
        }
        if (request.getAprovadoPor() != null) {
            controlePonto.setAprovadoPor(request.getAprovadoPor());
        }

        // Atualiza relacionamentos se fornecidos
        // Estabelecimento não faz parte do Request

        if (request.getProfissional() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissional())
                    .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + request.getProfissional()));
            controlePonto.setProfissional(profissional);
        }

        if (request.getMedico() != null) {
            Medicos medico = medicosRepository.findById(request.getMedico())
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedico()));
            controlePonto.setMedico(medico);
        }
    }
}

