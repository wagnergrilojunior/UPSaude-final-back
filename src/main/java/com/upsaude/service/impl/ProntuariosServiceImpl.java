package com.upsaude.service.impl;

import com.upsaude.api.request.ProntuariosRequest;
import com.upsaude.api.response.ProntuariosResponse;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Prontuarios;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ProntuariosMapper;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.ProntuariosRepository;
import com.upsaude.service.ProntuariosService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Prontuarios.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProntuariosServiceImpl implements ProntuariosService {

    private final ProntuariosRepository prontuariosRepository;
    private final ProntuariosMapper prontuariosMapper;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final PacienteRepository pacienteRepository;

    @Override
    @Transactional
    public ProntuariosResponse criar(ProntuariosRequest request) {
        log.debug("Criando novo prontuarios");

        validarDadosBasicos(request);

        Prontuarios prontuarios = prontuariosMapper.fromRequest(request);

        // Carrega e define o estabelecimento
        Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
        prontuarios.setEstabelecimento(estabelecimento);

        // Carrega e define o paciente
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
        prontuarios.setPaciente(paciente);

        prontuarios.setActive(true);

        Prontuarios prontuariosSalvo = prontuariosRepository.save(prontuarios);
        log.info("Prontuarios criado com sucesso. ID: {}", prontuariosSalvo.getId());

        return prontuariosMapper.toResponse(prontuariosSalvo);
    }

    @Override
    @Transactional
    public ProntuariosResponse obterPorId(UUID id) {
        log.debug("Buscando prontuarios por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do prontuarios é obrigatório");
        }

        Prontuarios prontuarios = prontuariosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prontuarios não encontrado com ID: " + id));

        return prontuariosMapper.toResponse(prontuarios);
    }

    @Override
    public Page<ProntuariosResponse> listar(Pageable pageable) {
        log.debug("Listando Prontuarios paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Prontuarios> prontuarios = prontuariosRepository.findAll(pageable);
        return prontuarios.map(prontuariosMapper::toResponse);
    }

    @Override
    public Page<ProntuariosResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando prontuários do estabelecimento: {}. Página: {}, Tamanho: {}",
                estabelecimentoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Page<Prontuarios> prontuarios = prontuariosRepository.findByEstabelecimentoId(estabelecimentoId, pageable);
        return prontuarios.map(prontuariosMapper::toResponse);
    }

    @Override
    @Transactional
    public ProntuariosResponse atualizar(UUID id, ProntuariosRequest request) {
        log.debug("Atualizando prontuarios. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do prontuarios é obrigatório");
        }

        validarDadosBasicos(request);

        Prontuarios prontuariosExistente = prontuariosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prontuarios não encontrado com ID: " + id));

        atualizarDadosProntuarios(prontuariosExistente, request);

        Prontuarios prontuariosAtualizado = prontuariosRepository.save(prontuariosExistente);
        log.info("Prontuarios atualizado com sucesso. ID: {}", prontuariosAtualizado.getId());

        return prontuariosMapper.toResponse(prontuariosAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo prontuarios. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do prontuarios é obrigatório");
        }

        Prontuarios prontuarios = prontuariosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prontuarios não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(prontuarios.getActive())) {
            throw new BadRequestException("Prontuarios já está inativo");
        }

        prontuarios.setActive(false);
        prontuariosRepository.save(prontuarios);
        log.info("Prontuarios excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(ProntuariosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do prontuarios são obrigatórios");
        }
        if (request.getEstabelecimentoId() == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
    }

        private void atualizarDadosProntuarios(Prontuarios prontuarios, ProntuariosRequest request) {
        // Atualiza estabelecimento se fornecido
        if (request.getEstabelecimentoId() != null) {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
            prontuarios.setEstabelecimento(estabelecimento);
        }

        // Atualiza paciente se fornecido
        if (request.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
            prontuarios.setPaciente(paciente);
        }

        // Atualiza outros campos
        if (request.getTipoRegistro() != null) {
            prontuarios.setTipoRegistro(request.getTipoRegistro());
        }
        if (request.getConteudo() != null) {
            prontuarios.setConteudo(request.getConteudo());
        }
        if (request.getCriadoPor() != null) {
            prontuarios.setCriadoPor(request.getCriadoPor());
        }
    }
}
