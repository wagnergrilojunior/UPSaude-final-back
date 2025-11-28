package com.upsaude.service.impl;

import com.upsaude.api.request.DeficienciasPacienteRequest;
import com.upsaude.api.response.DeficienciasPacienteResponse;
import com.upsaude.entity.DeficienciasPaciente;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Deficiencias;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DeficienciasPacienteMapper;
import com.upsaude.repository.DeficienciasPacienteRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.DeficienciasRepository;
import com.upsaude.service.DeficienciasPacienteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Deficiências de Paciente.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeficienciasPacienteServiceImpl implements DeficienciasPacienteService {

    private final DeficienciasPacienteRepository deficienciasPacienteRepository;
    private final DeficienciasPacienteMapper deficienciasPacienteMapper;
    private final PacienteRepository pacienteRepository;
    private final DeficienciasRepository deficienciasRepository;

    @Override
    @Transactional
    public DeficienciasPacienteResponse criar(DeficienciasPacienteRequest request) {
        log.debug("Criando nova ligação paciente-deficiência");

        validarDadosBasicos(request);

        DeficienciasPaciente deficienciasPaciente = deficienciasPacienteMapper.fromRequest(request);
        
        // Carrega e define as entidades relacionadas
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
        deficienciasPaciente.setPaciente(paciente);

        Deficiencias deficiencia = deficienciasRepository.findById(request.getDeficienciaId())
                .orElseThrow(() -> new NotFoundException("Deficiência não encontrada com ID: " + request.getDeficienciaId()));
        deficienciasPaciente.setDeficiencia(deficiencia);

        if (request.getPossuiLaudo() != null) {
            deficienciasPaciente.setPossuiLaudo(request.getPossuiLaudo());
        } else {
            deficienciasPaciente.setPossuiLaudo(false);
        }

        deficienciasPaciente.setActive(true);

        DeficienciasPaciente deficienciasPacienteSalvo = deficienciasPacienteRepository.save(deficienciasPaciente);
        log.info("Ligação paciente-deficiência criada com sucesso. ID: {}", deficienciasPacienteSalvo.getId());

        return deficienciasPacienteMapper.toResponse(deficienciasPacienteSalvo);
    }

    @Override
    @Transactional
    public DeficienciasPacienteResponse obterPorId(UUID id) {
        log.debug("Buscando ligação paciente-deficiência por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da ligação paciente-deficiência é obrigatório");
        }

        DeficienciasPaciente deficienciasPaciente = deficienciasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ligação paciente-deficiência não encontrada com ID: " + id));

        return deficienciasPacienteMapper.toResponse(deficienciasPaciente);
    }

    @Override
    public Page<DeficienciasPacienteResponse> listar(Pageable pageable) {
        log.debug("Listando ligações paciente-deficiência paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<DeficienciasPaciente> deficienciasPacientes = deficienciasPacienteRepository.findAll(pageable);
        return deficienciasPacientes.map(deficienciasPacienteMapper::toResponse);
    }

    @Override
    @Transactional
    public DeficienciasPacienteResponse atualizar(UUID id, DeficienciasPacienteRequest request) {
        log.debug("Atualizando ligação paciente-deficiência. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da ligação paciente-deficiência é obrigatório");
        }

        validarDadosBasicos(request);

        DeficienciasPaciente deficienciasPacienteExistente = deficienciasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ligação paciente-deficiência não encontrada com ID: " + id));

        atualizarDadosDeficienciasPaciente(deficienciasPacienteExistente, request);

        DeficienciasPaciente deficienciasPacienteAtualizado = deficienciasPacienteRepository.save(deficienciasPacienteExistente);
        log.info("Ligação paciente-deficiência atualizada com sucesso. ID: {}", deficienciasPacienteAtualizado.getId());

        return deficienciasPacienteMapper.toResponse(deficienciasPacienteAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo ligação paciente-deficiência. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da ligação paciente-deficiência é obrigatório");
        }

        DeficienciasPaciente deficienciasPaciente = deficienciasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ligação paciente-deficiência não encontrada com ID: " + id));

        if (Boolean.FALSE.equals(deficienciasPaciente.getActive())) {
            throw new BadRequestException("Ligação paciente-deficiência já está inativa");
        }

        deficienciasPaciente.setActive(false);
        deficienciasPacienteRepository.save(deficienciasPaciente);
        log.info("Ligação paciente-deficiência excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(DeficienciasPacienteRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da ligação paciente-deficiência são obrigatórios");
        }
        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        if (request.getDeficienciaId() == null) {
            throw new BadRequestException("ID da deficiência é obrigatório");
        }
    }

    private void atualizarDadosDeficienciasPaciente(DeficienciasPaciente deficienciasPaciente, DeficienciasPacienteRequest request) {
        // Atualiza campos simples
        if (request.getDataDiagnostico() != null) {
            deficienciasPaciente.setDataDiagnostico(request.getDataDiagnostico());
        }
        if (request.getPossuiLaudo() != null) {
            deficienciasPaciente.setPossuiLaudo(request.getPossuiLaudo());
        }
        if (request.getObservacoes() != null) {
            deficienciasPaciente.setObservacoes(request.getObservacoes());
        }

        // Atualiza relacionamentos se fornecidos
        if (request.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
            deficienciasPaciente.setPaciente(paciente);
        }

        if (request.getDeficienciaId() != null) {
            Deficiencias deficiencia = deficienciasRepository.findById(request.getDeficienciaId())
                    .orElseThrow(() -> new NotFoundException("Deficiência não encontrada com ID: " + request.getDeficienciaId()));
            deficienciasPaciente.setDeficiencia(deficiencia);
        }
    }
}

