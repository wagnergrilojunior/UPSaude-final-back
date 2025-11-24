package com.upsaude.service.impl;

import com.upsaude.api.request.ProfissionaisSaudeRequest;
import com.upsaude.api.response.ProfissionaisSaudeResponse;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ProfissionaisSaudeMapper;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.service.ProfissionaisSaudeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import org.springframework.util.StringUtils;

/**
 * Implementação do serviço de gerenciamento de Profissionais de Saúde.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProfissionaisSaudeServiceImpl implements ProfissionaisSaudeService {

    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final ProfissionaisSaudeMapper profissionaisSaudeMapper;

    @Override
    @Transactional
    public ProfissionaisSaudeResponse criar(ProfissionaisSaudeRequest request) {
        log.debug("Criando novo profissional de saúde");

        validarDadosBasicos(request);

        ProfissionaisSaude profissional = profissionaisSaudeMapper.fromRequest(request);
        profissional.setActive(true);

        ProfissionaisSaude profissionalSalvo = profissionaisSaudeRepository.save(profissional);
        log.info("Profissional de saúde criado com sucesso. ID: {}", profissionalSalvo.getId());

        return profissionaisSaudeMapper.toResponse(profissionalSalvo);
    }

    @Override
    @Transactional
    public ProfissionaisSaudeResponse obterPorId(UUID id) {
        log.debug("Buscando profissional de saúde por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do profissional de saúde é obrigatório");
        }

        ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + id));

        return profissionaisSaudeMapper.toResponse(profissional);
    }

    @Override
    public Page<ProfissionaisSaudeResponse> listar(Pageable pageable) {
        log.debug("Listando profissionais de saúde paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<ProfissionaisSaude> profissionais = profissionaisSaudeRepository.findAll(pageable);
        return profissionais.map(profissionaisSaudeMapper::toResponse);
    }

    @Override
    @Transactional
    public ProfissionaisSaudeResponse atualizar(UUID id, ProfissionaisSaudeRequest request) {
        log.debug("Atualizando profissional de saúde. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do profissional de saúde é obrigatório");
        }

        validarDadosBasicos(request);

        ProfissionaisSaude profissionalExistente = profissionaisSaudeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + id));

        atualizarDadosProfissional(profissionalExistente, request);

        ProfissionaisSaude profissionalAtualizado = profissionaisSaudeRepository.save(profissionalExistente);
        log.info("Profissional de saúde atualizado com sucesso. ID: {}", profissionalAtualizado.getId());

        return profissionaisSaudeMapper.toResponse(profissionalAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo profissional de saúde. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do profissional de saúde é obrigatório");
        }

        ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(profissional.getActive())) {
            throw new BadRequestException("Profissional de saúde já está inativo");
        }

        profissional.setActive(false);
        profissionaisSaudeRepository.save(profissional);
        log.info("Profissional de saúde excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(ProfissionaisSaudeRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do profissional de saúde são obrigatórios");
        }
    }

    private void atualizarDadosProfissional(ProfissionaisSaude profissional, ProfissionaisSaudeRequest request) {
        ProfissionaisSaude profissionalAtualizado = profissionaisSaudeMapper.fromRequest(request);

        profissional.setNomeCompleto(profissionalAtualizado.getNomeCompleto());
        profissional.setCpf(profissionalAtualizado.getCpf());
        profissional.setRegistroProfissional(profissionalAtualizado.getRegistroProfissional());
        profissional.setConselho(profissionalAtualizado.getConselho());
        profissional.setUfRegistro(profissionalAtualizado.getUfRegistro());
        profissional.setTelefone(profissionalAtualizado.getTelefone());
        profissional.setEmail(profissionalAtualizado.getEmail());
    }
}

