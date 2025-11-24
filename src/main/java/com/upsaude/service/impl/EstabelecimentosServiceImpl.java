package com.upsaude.service.impl;

import com.upsaude.api.request.EstabelecimentosRequest;
import com.upsaude.api.response.EstabelecimentosResponse;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EstabelecimentosMapper;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.service.EstabelecimentosService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Estabelecimentos.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EstabelecimentosServiceImpl implements EstabelecimentosService {

    private final EstabelecimentosRepository estabelecimentosRepository;
    private final EstabelecimentosMapper estabelecimentosMapper;

    @Override
    @Transactional
    public EstabelecimentosResponse criar(EstabelecimentosRequest request) {
        log.debug("Criando novo estabelecimento");

        validarDadosBasicos(request);

        Estabelecimentos estabelecimento = estabelecimentosMapper.fromRequest(request);
        estabelecimento.setActive(true);

        Estabelecimentos estabelecimentoSalvo = estabelecimentosRepository.save(estabelecimento);
        log.info("Estabelecimento criado com sucesso. ID: {}", estabelecimentoSalvo.getId());

        return estabelecimentosMapper.toResponse(estabelecimentoSalvo);
    }

    @Override
    @Transactional
    public EstabelecimentosResponse obterPorId(UUID id) {
        log.debug("Buscando estabelecimento por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Estabelecimentos estabelecimento = estabelecimentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + id));

        return estabelecimentosMapper.toResponse(estabelecimento);
    }

    @Override
    public Page<EstabelecimentosResponse> listar(Pageable pageable) {
        log.debug("Listando estabelecimentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Estabelecimentos> estabelecimentos = estabelecimentosRepository.findAll(pageable);
        return estabelecimentos.map(estabelecimentosMapper::toResponse);
    }

    @Override
    @Transactional
    public EstabelecimentosResponse atualizar(UUID id, EstabelecimentosRequest request) {
        log.debug("Atualizando estabelecimento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        validarDadosBasicos(request);

        Estabelecimentos estabelecimentoExistente = estabelecimentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + id));

        atualizarDadosEstabelecimento(estabelecimentoExistente, request);

        Estabelecimentos estabelecimentoAtualizado = estabelecimentosRepository.save(estabelecimentoExistente);
        log.info("Estabelecimento atualizado com sucesso. ID: {}", estabelecimentoAtualizado.getId());

        return estabelecimentosMapper.toResponse(estabelecimentoAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo estabelecimento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Estabelecimentos estabelecimento = estabelecimentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(estabelecimento.getActive())) {
            throw new BadRequestException("Estabelecimento já está inativo");
        }

        estabelecimento.setActive(false);
        estabelecimentosRepository.save(estabelecimento);
        log.info("Estabelecimento excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(EstabelecimentosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do estabelecimento são obrigatórios");
        }
    }

    private void atualizarDadosEstabelecimento(Estabelecimentos estabelecimento, EstabelecimentosRequest request) {
        Estabelecimentos estabelecimentoAtualizado = estabelecimentosMapper.fromRequest(request);

        estabelecimento.setNome(estabelecimentoAtualizado.getNome());
        estabelecimento.setTipo(estabelecimentoAtualizado.getTipo());
        estabelecimento.setEnderecoJson(estabelecimentoAtualizado.getEnderecoJson());
        estabelecimento.setContatoJson(estabelecimentoAtualizado.getContatoJson());
        estabelecimento.setMetadados(estabelecimentoAtualizado.getMetadados());
        estabelecimento.setEnderecos(estabelecimentoAtualizado.getEnderecos());
    }
}

