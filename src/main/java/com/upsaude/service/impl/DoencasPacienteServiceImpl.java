package com.upsaude.service.impl;

import com.upsaude.api.request.DoencasPacienteRequest;
import com.upsaude.api.response.DoencasPacienteResponse;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Doencas;
import com.upsaude.entity.DoencasPaciente;
import com.upsaude.entity.Paciente;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DoencasPacienteMapper;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.repository.DoencasPacienteRepository;
import com.upsaude.repository.DoencasRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.service.DoencasPacienteService;
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
 * Implementação do serviço de gerenciamento de DoencasPaciente.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DoencasPacienteServiceImpl implements DoencasPacienteService {

    private final DoencasPacienteRepository doencasPacienteRepository;
    private final DoencasPacienteMapper doencasPacienteMapper;
    private final PacienteRepository pacienteRepository;
    private final DoencasRepository doencasRepository;
    private final CidDoencasRepository cidDoencasRepository;

    @Override
    @Transactional
    @CacheEvict(value = "doencaspaciente", allEntries = true)
    public DoencasPacienteResponse criar(DoencasPacienteRequest request) {
        log.debug("Criando novo registro de doença do paciente");

        validarDadosBasicos(request);

        DoencasPaciente doencasPaciente = doencasPacienteMapper.fromRequest(request);

        // Carrega e define relacionamentos obrigatórios
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
        doencasPaciente.setPaciente(paciente);

        Doencas doenca = doencasRepository.findById(request.getDoencaId())
                .orElseThrow(() -> new NotFoundException("Doença não encontrada com ID: " + request.getDoencaId()));
        doencasPaciente.setDoenca(doenca);

        // Define estabelecimento e tenant do paciente
        if (paciente.getEstabelecimento() != null) {
            doencasPaciente.setEstabelecimento(paciente.getEstabelecimento());
        }
        if (paciente.getTenant() != null) {
            doencasPaciente.setTenant(paciente.getTenant());
        }

        // Carrega CID principal se fornecido
        if (request.getCidPrincipalId() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipalId())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipalId()));
            doencasPaciente.setCidPrincipal(cidPrincipal);
        }

        doencasPaciente.setActive(true);

        DoencasPaciente doencasPacienteSalvo = doencasPacienteRepository.save(doencasPaciente);
        log.info("Registro de doença do paciente criado com sucesso. ID: {}", doencasPacienteSalvo.getId());

        return doencasPacienteMapper.toResponse(doencasPacienteSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "doencaspaciente", key = "#id")
    public DoencasPacienteResponse obterPorId(UUID id) {
        log.debug("Buscando registro de doença do paciente por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do registro é obrigatório");
        }

        DoencasPaciente doencasPaciente = doencasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro de doença do paciente não encontrado com ID: " + id));

        return doencasPacienteMapper.toResponse(doencasPaciente);
    }

    @Override
    public Page<DoencasPacienteResponse> listar(Pageable pageable) {
        log.debug("Listando registros de doenças do paciente paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<DoencasPaciente> registros = doencasPacienteRepository.findAll(pageable);
        return registros.map(doencasPacienteMapper::toResponse);
    }

    @Override
    public Page<DoencasPacienteResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        log.debug("Listando doenças do paciente: {}. Página: {}, Tamanho: {}",
                pacienteId, pageable.getPageNumber(), pageable.getPageSize());

        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Page<DoencasPaciente> registros = doencasPacienteRepository.findByPacienteId(pacienteId, pageable);
        return registros.map(doencasPacienteMapper::toResponse);
    }

    @Override
    public Page<DoencasPacienteResponse> listarPorDoenca(UUID doencaId, Pageable pageable) {
        log.debug("Listando pacientes com a doença: {}. Página: {}, Tamanho: {}",
                doencaId, pageable.getPageNumber(), pageable.getPageSize());

        if (doencaId == null) {
            throw new BadRequestException("ID da doença é obrigatório");
        }

        Page<DoencasPaciente> registros = doencasPacienteRepository.findByDoencaId(doencaId, pageable);
        return registros.map(doencasPacienteMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "doencaspaciente", key = "#id")
    public DoencasPacienteResponse atualizar(UUID id, DoencasPacienteRequest request) {
        log.debug("Atualizando registro de doença do paciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do registro é obrigatório");
        }

        validarDadosBasicos(request);

        DoencasPaciente doencasPacienteExistente = doencasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro de doença do paciente não encontrado com ID: " + id));

        atualizarDadosDoencasPaciente(doencasPacienteExistente, request);

        DoencasPaciente doencasPacienteAtualizado = doencasPacienteRepository.save(doencasPacienteExistente);
        log.info("Registro de doença do paciente atualizado com sucesso. ID: {}", doencasPacienteAtualizado.getId());

        return doencasPacienteMapper.toResponse(doencasPacienteAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "doencaspaciente", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo registro de doença do paciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do registro é obrigatório");
        }

        DoencasPaciente doencasPaciente = doencasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro de doença do paciente não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(doencasPaciente.getActive())) {
            throw new BadRequestException("Registro já está inativo");
        }

        doencasPaciente.setActive(false);
        doencasPacienteRepository.save(doencasPaciente);
        log.info("Registro de doença do paciente excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(DoencasPacienteRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do registro são obrigatórios");
        }
        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        if (request.getDoencaId() == null) {
            throw new BadRequestException("ID da doença é obrigatório");
        }
    }

    private void atualizarDadosDoencasPaciente(DoencasPaciente doencasPaciente, DoencasPacienteRequest request) {
        if (request.getDiagnostico() != null) {
            doencasPaciente.setDiagnostico(request.getDiagnostico());
        }
        if (request.getAcompanhamento() != null) {
            doencasPaciente.setAcompanhamento(request.getAcompanhamento());
        }
        if (request.getTratamentoAtual() != null) {
            doencasPaciente.setTratamentoAtual(request.getTratamentoAtual());
        }
        if (request.getObservacoes() != null) {
            doencasPaciente.setObservacoes(request.getObservacoes());
        }

        // Atualiza relacionamentos se fornecidos
        if (request.getCidPrincipalId() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipalId())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipalId()));
            doencasPaciente.setCidPrincipal(cidPrincipal);
        }
    }
}

