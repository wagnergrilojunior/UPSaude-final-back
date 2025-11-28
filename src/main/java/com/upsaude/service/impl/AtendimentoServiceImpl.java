package com.upsaude.service.impl;

import com.upsaude.api.request.AtendimentoRequest;
import com.upsaude.api.response.AtendimentoResponse;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.CidDoencas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.AtendimentoMapper;
import com.upsaude.repository.AtendimentoRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.service.AtendimentoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Atendimentos.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AtendimentoServiceImpl implements AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;
    private final AtendimentoMapper atendimentoMapper;
    private final PacienteRepository pacienteRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final CidDoencasRepository cidDoencasRepository;

    @Override
    @Transactional
    public AtendimentoResponse criar(AtendimentoRequest request) {
        log.debug("Criando novo atendimento para paciente: {}", request.getPacienteId());

        validarDadosBasicos(request);

        Atendimento atendimento = atendimentoMapper.fromRequest(request);

        // Carrega e define o paciente
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
        atendimento.setPaciente(paciente);

        // Carrega e define o profissional
        ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissionalId())
                .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + request.getProfissionalId()));
        atendimento.setProfissional(profissional);

        // CID principal é opcional
        if (request.getCidPrincipalId() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipalId())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipalId()));
            atendimento.setCidPrincipal(cidPrincipal);
        }

        atendimento.setActive(true);

        Atendimento atendimentoSalvo = atendimentoRepository.save(atendimento);
        log.info("Atendimento criado com sucesso. ID: {}", atendimentoSalvo.getId());

        return atendimentoMapper.toResponse(atendimentoSalvo);
    }

    @Override
    @Transactional
    public AtendimentoResponse obterPorId(UUID id) {
        log.debug("Buscando atendimento por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do atendimento é obrigatório");
        }

        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + id));

        return atendimentoMapper.toResponse(atendimento);
    }

    @Override
    public Page<AtendimentoResponse> listar(Pageable pageable) {
        log.debug("Listando atendimentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Atendimento> atendimentos = atendimentoRepository.findAll(pageable);
        return atendimentos.map(atendimentoMapper::toResponse);
    }

    @Override
    public Page<AtendimentoResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        log.debug("Listando atendimentos do paciente: {}. Página: {}, Tamanho: {}",
                pacienteId, pageable.getPageNumber(), pageable.getPageSize());

        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Page<Atendimento> atendimentos = atendimentoRepository.findByPacienteIdOrderByDataHoraDesc(pacienteId, pageable);
        return atendimentos.map(atendimentoMapper::toResponse);
    }

    @Override
    public Page<AtendimentoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        log.debug("Listando atendimentos do profissional: {}. Página: {}, Tamanho: {}",
                profissionalId, pageable.getPageNumber(), pageable.getPageSize());

        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }

        Page<Atendimento> atendimentos = atendimentoRepository.findByProfissionalIdOrderByDataHoraDesc(profissionalId, pageable);
        return atendimentos.map(atendimentoMapper::toResponse);
    }

    @Override
    @Transactional
    public AtendimentoResponse atualizar(UUID id, AtendimentoRequest request) {
        log.debug("Atualizando atendimento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do atendimento é obrigatório");
        }

        validarDadosBasicos(request);

        Atendimento atendimentoExistente = atendimentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + id));

        atualizarDadosAtendimento(atendimentoExistente, request);

        Atendimento atendimentoAtualizado = atendimentoRepository.save(atendimentoExistente);
        log.info("Atendimento atualizado com sucesso. ID: {}", atendimentoAtualizado.getId());

        return atendimentoMapper.toResponse(atendimentoAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo atendimento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do atendimento é obrigatório");
        }

        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(atendimento.getActive())) {
            throw new BadRequestException("Atendimento já está inativo");
        }

        atendimento.setActive(false);
        atendimentoRepository.save(atendimento);
        log.info("Atendimento excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(AtendimentoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do atendimento são obrigatórios");
        }
        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        if (request.getProfissionalId() == null) {
            throw new BadRequestException("ID do profissional de saúde é obrigatório");
        }
        if (request.getDataHora() == null) {
            throw new BadRequestException("Data e hora do atendimento são obrigatórias");
        }
    }

    private void atualizarDadosAtendimento(Atendimento atendimento, AtendimentoRequest request) {
        // Atualiza data/hora
        if (request.getDataHora() != null) {
            atendimento.setDataHora(request.getDataHora());
        }

        // Atualiza campos de texto
        if (request.getTipoAtendimento() != null) {
            atendimento.setTipoAtendimento(request.getTipoAtendimento());
        }
        if (request.getMotivo() != null) {
            atendimento.setMotivo(request.getMotivo());
        }
        if (request.getAnotacoes() != null) {
            atendimento.setAnotacoes(request.getAnotacoes());
        }

        // Atualiza relacionamentos se fornecidos
        if (request.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
            atendimento.setPaciente(paciente);
        }

        if (request.getProfissionalId() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissionalId())
                    .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + request.getProfissionalId()));
            atendimento.setProfissional(profissional);
        }

        // CID principal é opcional
        if (request.getCidPrincipalId() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipalId())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipalId()));
            atendimento.setCidPrincipal(cidPrincipal);
        } else {
            // Se não fornecido, remove a relação
            atendimento.setCidPrincipal(null);
        }
    }
}

