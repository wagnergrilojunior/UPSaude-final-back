package com.upsaude.service.impl;

import com.upsaude.api.request.ExamesRequest;
import com.upsaude.api.response.ExamesResponse;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Exames;
import com.upsaude.entity.Paciente;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ExamesMapper;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.ExamesRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.service.ExamesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Exames.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExamesServiceImpl implements ExamesService {

    private final ExamesRepository examesRepository;
    private final ExamesMapper examesMapper;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final PacienteRepository pacienteRepository;

    @Override
    @Transactional
    public ExamesResponse criar(ExamesRequest request) {
        log.debug("Criando novo exames");

        validarDadosBasicos(request);

        Exames exames = examesMapper.fromRequest(request);

        // Carrega e define o estabelecimento
        Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
        exames.setEstabelecimento(estabelecimento);

        // Carrega e define o paciente
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
        exames.setPaciente(paciente);

        exames.setActive(true);

        Exames examesSalvo = examesRepository.save(exames);
        log.info("Exames criado com sucesso. ID: {}", examesSalvo.getId());

        return examesMapper.toResponse(examesSalvo);
    }

    @Override
    @Transactional
    public ExamesResponse obterPorId(UUID id) {
        log.debug("Buscando exames por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do exames é obrigatório");
        }

        Exames exames = examesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exames não encontrado com ID: " + id));

        return examesMapper.toResponse(exames);
    }

    @Override
    public Page<ExamesResponse> listar(Pageable pageable) {
        log.debug("Listando Exames paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Exames> exames = examesRepository.findAll(pageable);
        return exames.map(examesMapper::toResponse);
    }

    @Override
    public Page<ExamesResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando exames do estabelecimento: {}. Página: {}, Tamanho: {}",
                estabelecimentoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Page<Exames> exames = examesRepository.findByEstabelecimentoIdOrderByDataExameDesc(estabelecimentoId, pageable);
        return exames.map(examesMapper::toResponse);
    }

    @Override
    @Transactional
    public ExamesResponse atualizar(UUID id, ExamesRequest request) {
        log.debug("Atualizando exames. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do exames é obrigatório");
        }

        validarDadosBasicos(request);

        Exames examesExistente = examesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exames não encontrado com ID: " + id));

        atualizarDadosExames(examesExistente, request);

        Exames examesAtualizado = examesRepository.save(examesExistente);
        log.info("Exames atualizado com sucesso. ID: {}", examesAtualizado.getId());

        return examesMapper.toResponse(examesAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo exames. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do exames é obrigatório");
        }

        Exames exames = examesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exames não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(exames.getActive())) {
            throw new BadRequestException("Exames já está inativo");
        }

        exames.setActive(false);
        examesRepository.save(exames);
        log.info("Exames excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(ExamesRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do exames são obrigatórios");
        }
        if (request.getEstabelecimentoId() == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
    }

        private void atualizarDadosExames(Exames exames, ExamesRequest request) {
        // Atualiza estabelecimento se fornecido
        if (request.getEstabelecimentoId() != null) {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
            exames.setEstabelecimento(estabelecimento);
        }

        // Atualiza paciente se fornecido
        if (request.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
            exames.setPaciente(paciente);
        }

        // Atualiza outros campos
        if (request.getTipoExame() != null) {
            exames.setTipoExame(request.getTipoExame());
        }
        if (request.getDataExame() != null) {
            exames.setDataExame(request.getDataExame());
        }
        if (request.getResultados() != null) {
            exames.setResultados(request.getResultados());
        }
    }
}
