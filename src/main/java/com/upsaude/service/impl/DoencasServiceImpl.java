package com.upsaude.service.impl;

import com.upsaude.api.request.DoencasRequest;
import com.upsaude.api.response.DoencasResponse;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Doencas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DoencasMapper;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.repository.DoencasRepository;
import com.upsaude.service.DoencasService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Doencas.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DoencasServiceImpl implements DoencasService {

    private final DoencasRepository doencasRepository;
    private final DoencasMapper doencasMapper;
    private final CidDoencasRepository cidDoencasRepository;

    @Override
    @Transactional
    public DoencasResponse criar(DoencasRequest request) {
        log.debug("Criando nova doença");

        validarDadosBasicos(request);

        Doencas doenca = doencasMapper.fromRequest(request);

        // Carrega e define CID principal se fornecido
        if (request.getCidPrincipalId() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipalId())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipalId()));
            doenca.setCidPrincipal(cidPrincipal);
        }

        doenca.setActive(true);

        Doencas doencaSalva = doencasRepository.save(doenca);
        log.info("Doença criada com sucesso. ID: {}", doencaSalva.getId());

        return doencasMapper.toResponse(doencaSalva);
    }

    @Override
    @Transactional
    public DoencasResponse obterPorId(UUID id) {
        log.debug("Buscando doença por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da doença é obrigatório");
        }

        Doencas doenca = doencasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doença não encontrada com ID: " + id));

        return doencasMapper.toResponse(doenca);
    }

    @Override
    public Page<DoencasResponse> listar(Pageable pageable) {
        log.debug("Listando doenças paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Doencas> doencas = doencasRepository.findAll(pageable);
        return doencas.map(doencasMapper::toResponse);
    }

    @Override
    public Page<DoencasResponse> listarPorNome(String nome, Pageable pageable) {
        log.debug("Listando doenças por nome: {}. Página: {}, Tamanho: {}",
                nome, pageable.getPageNumber(), pageable.getPageSize());

        if (nome == null || nome.trim().isEmpty()) {
            throw new BadRequestException("Nome é obrigatório para busca");
        }

        Page<Doencas> doencas = doencasRepository.findByNomeContainingIgnoreCase(nome, pageable);
        return doencas.map(doencasMapper::toResponse);
    }

    @Override
    public Page<DoencasResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando doenças do estabelecimento: {}. Página: {}, Tamanho: {}",
                estabelecimentoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Page<Doencas> doencas = doencasRepository.findByEstabelecimentoId(estabelecimentoId, pageable);
        return doencas.map(doencasMapper::toResponse);
    }

    @Override
    public Page<DoencasResponse> listarPorCodigoCid(String codigoCid, Pageable pageable) {
        log.debug("Listando doenças por código CID: {}. Página: {}, Tamanho: {}",
                codigoCid, pageable.getPageNumber(), pageable.getPageSize());

        if (codigoCid == null || codigoCid.trim().isEmpty()) {
            throw new BadRequestException("Código CID é obrigatório para busca");
        }

        Page<Doencas> doencas = doencasRepository.findByCodigoCid(codigoCid, pageable);
        return doencas.map(doencasMapper::toResponse);
    }

    @Override
    @Transactional
    public DoencasResponse atualizar(UUID id, DoencasRequest request) {
        log.debug("Atualizando doença. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da doença é obrigatório");
        }

        validarDadosBasicos(request);

        Doencas doencaExistente = doencasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doença não encontrada com ID: " + id));

        atualizarDadosDoenca(doencaExistente, request);

        Doencas doencaAtualizada = doencasRepository.save(doencaExistente);
        log.info("Doença atualizada com sucesso. ID: {}", doencaAtualizada.getId());

        return doencasMapper.toResponse(doencaAtualizada);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo doença. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da doença é obrigatório");
        }

        Doencas doenca = doencasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doença não encontrada com ID: " + id));

        if (Boolean.FALSE.equals(doenca.getActive())) {
            throw new BadRequestException("Doença já está inativa");
        }

        doenca.setActive(false);
        doencasRepository.save(doenca);
        log.info("Doença excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(DoencasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da doença são obrigatórios");
        }
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new BadRequestException("Nome da doença é obrigatório");
        }
    }

    private void atualizarDadosDoenca(Doencas doenca, DoencasRequest request) {
        if (request.getNome() != null) {
            doenca.setNome(request.getNome());
        }
        if (request.getNomeCientifico() != null) {
            doenca.setNomeCientifico(request.getNomeCientifico());
        }
        if (request.getCodigoInterno() != null) {
            doenca.setCodigoInterno(request.getCodigoInterno());
        }
        if (request.getClassificacao() != null) {
            doenca.setClassificacao(request.getClassificacao());
        }
        if (request.getSintomas() != null) {
            doenca.setSintomas(request.getSintomas());
        }
        if (request.getTratamentoPadrao() != null) {
            doenca.setTratamentoPadrao(request.getTratamentoPadrao());
        }
        if (request.getEpidemiologia() != null) {
            doenca.setEpidemiologia(request.getEpidemiologia());
        }
        if (request.getDescricao() != null) {
            doenca.setDescricao(request.getDescricao());
        }
        if (request.getCausas() != null) {
            doenca.setCausas(request.getCausas());
        }
        if (request.getFisiopatologia() != null) {
            doenca.setFisiopatologia(request.getFisiopatologia());
        }
        if (request.getPrognostico() != null) {
            doenca.setPrognostico(request.getPrognostico());
        }
        if (request.getObservacoes() != null) {
            doenca.setObservacoes(request.getObservacoes());
        }

        // Atualiza CID principal se fornecido
        if (request.getCidPrincipalId() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipalId())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipalId()));
            doenca.setCidPrincipal(cidPrincipal);
        }
    }
}

