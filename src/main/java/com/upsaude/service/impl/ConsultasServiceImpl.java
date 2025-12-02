package com.upsaude.service.impl;

import com.upsaude.api.request.ConsultasRequest;
import com.upsaude.api.response.ConsultasResponse;
import com.upsaude.entity.Consultas;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ConsultasMapper;
import com.upsaude.repository.ConsultasRepository;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.service.ConsultasService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Consultas (Agendamentos).
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultasServiceImpl implements ConsultasService {

    private final ConsultasRepository consultasRepository;
    private final ConsultasMapper consultasMapper;
    private final EstabelecimentosRepository estabelecimentosRepository;

    @Override
    @Transactional
    @CacheEvict(value = "consultas", allEntries = true)
    public ConsultasResponse criar(ConsultasRequest request) {
        log.debug("Criando nova consulta");

        validarDadosBasicos(request);

        Consultas consulta = consultasMapper.fromRequest(request);
        consulta.setActive(true);

        Consultas consultaSalva = consultasRepository.save(consulta);
        log.info("Consulta criada com sucesso. ID: {}", consultaSalva.getId());

        return consultasMapper.toResponse(consultaSalva);
    }

    @Override
    @Transactional
    @Cacheable(value = "consultas", key = "#id")
    public ConsultasResponse obterPorId(UUID id) {
        log.debug("Buscando consulta por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da consulta é obrigatório");
        }

        Consultas consulta = consultasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Consulta não encontrada com ID: " + id));

        return consultasMapper.toResponse(consulta);
    }

    @Override
    public Page<ConsultasResponse> listar(Pageable pageable) {
        log.debug("Listando consultas paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Consultas> consultas = consultasRepository.findAll(pageable);
        return consultas.map(consultasMapper::toResponse);
    }

    @Override
    public Page<ConsultasResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando consultas do estabelecimento: {}. Página: {}, Tamanho: {}",
                estabelecimentoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Page<Consultas> consultas = consultasRepository.findByEstabelecimentoIdOrderByInformacoesDataConsultaDesc(estabelecimentoId, pageable);
        return consultas.map(consultasMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "consultas", key = "#id")
    public ConsultasResponse atualizar(UUID id, ConsultasRequest request) {
        log.debug("Atualizando consulta. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da consulta é obrigatório");
        }

        validarDadosBasicos(request);

        Consultas consultaExistente = consultasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Consulta não encontrada com ID: " + id));

        atualizarDadosConsulta(consultaExistente, request);

        Consultas consultaAtualizada = consultasRepository.save(consultaExistente);
        log.info("Consulta atualizada com sucesso. ID: {}", consultaAtualizada.getId());

        return consultasMapper.toResponse(consultaAtualizada);
    }

    @Override
    @Transactional
    @CacheEvict(value = "consultas", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo consulta. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da consulta é obrigatório");
        }

        Consultas consulta = consultasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Consulta não encontrada com ID: " + id));

        if (Boolean.FALSE.equals(consulta.getActive())) {
            throw new BadRequestException("Consulta já está inativa");
        }

        consulta.setActive(false);
        consultasRepository.save(consulta);
        log.info("Consulta excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(ConsultasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da consulta são obrigatórios");
        }
        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        if (request.getInformacoes() == null) {
            throw new BadRequestException("Informações da consulta são obrigatórias");
        }
        if (request.getInformacoes().getDataConsulta() == null) {
            throw new BadRequestException("Data da consulta é obrigatória");
        }
    }

    private void atualizarDadosConsulta(Consultas consulta, ConsultasRequest request) {
        Consultas consultaAtualizada = consultasMapper.fromRequest(request);

        consulta.setPaciente(consultaAtualizada.getPaciente());
        consulta.setMedico(consultaAtualizada.getMedico());
        consulta.setProfissionalSaude(consultaAtualizada.getProfissionalSaude());
        consulta.setEspecialidade(consultaAtualizada.getEspecialidade());
        consulta.setConvenio(consultaAtualizada.getConvenio());
        consulta.setCidPrincipal(consultaAtualizada.getCidPrincipal());
        consulta.setInformacoes(consultaAtualizada.getInformacoes());
        consulta.setAnamnese(consultaAtualizada.getAnamnese());
        consulta.setDiagnostico(consultaAtualizada.getDiagnostico());
        consulta.setPrescricao(consultaAtualizada.getPrescricao());
        consulta.setExamesSolicitados(consultaAtualizada.getExamesSolicitados());
        consulta.setEncaminhamento(consultaAtualizada.getEncaminhamento());
        consulta.setAtestado(consultaAtualizada.getAtestado());
        consulta.setObservacoes(consultaAtualizada.getObservacoes());
        consulta.setObservacoesInternas(consultaAtualizada.getObservacoesInternas());
    }
}

