package com.upsaude.service.impl;

import com.upsaude.api.request.DoencasRequest;
import com.upsaude.api.response.DoencasResponse;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Doencas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DoencasMapper;
import com.upsaude.mapper.embeddable.ClassificacaoDoencaMapper;
import com.upsaude.mapper.embeddable.SintomasDoencaMapper;
import com.upsaude.mapper.embeddable.TratamentoPadraoDoencaMapper;
import com.upsaude.mapper.embeddable.EpidemiologiaDoencaMapper;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.repository.DoencasRepository;
import com.upsaude.service.DoencasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ClassificacaoDoencaMapper classificacaoDoencaMapper;
    private final SintomasDoencaMapper sintomasDoencaMapper;
    private final TratamentoPadraoDoencaMapper tratamentoPadraoDoencaMapper;
    private final EpidemiologiaDoencaMapper epidemiologiaDoencaMapper;
    private final CidDoencasRepository cidDoencasRepository;

    @Override
    @Transactional
    @CacheEvict(value = "doencas", allEntries = true)
    public DoencasResponse criar(DoencasRequest request) {
        log.debug("Criando nova doença. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar doença com request nulo");
            throw new BadRequestException("Dados da doença são obrigatórios");
        }

        try {
            validarDadosBasicos(request);

            Doencas doenca = doencasMapper.fromRequest(request);

            // Carrega e define CID principal se fornecido
            if (request.getCidPrincipal() != null) {
                CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipal())
                        .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipal()));
                doenca.setCidPrincipal(cidPrincipal);
            }

            doenca.setActive(true);

            Doencas doencaSalva = doencasRepository.save(doenca);
            log.info("Doença criada com sucesso. ID: {}", doencaSalva.getId());

            return doencasMapper.toResponse(doencaSalva);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar doença. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar doença. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar doença. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "doencas", key = "#id")
    public DoencasResponse obterPorId(UUID id) {
        log.debug("Buscando doença por ID: {} (cache miss)", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de doença");
            throw new BadRequestException("ID da doença é obrigatório");
        }

        try {
            Doencas doenca = doencasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Doença não encontrada com ID: " + id));

            log.debug("Doença encontrada. ID: {}", id);
            return doencasMapper.toResponse(doenca);
        } catch (NotFoundException e) {
            log.warn("Doença não encontrada. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar doença. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar doença. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoencasResponse> listar(Pageable pageable) {
        log.debug("Listando doenças paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Doencas> doencas = doencasRepository.findAll(pageable);
            log.debug("Listagem de doenças concluída. Total de elementos: {}", doencas.getTotalElements());
            return doencas.map(doencasMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar doenças. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar doenças", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar doenças. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoencasResponse> listarPorNome(String nome, Pageable pageable) {
        log.debug("Listando doenças por nome: {}. Página: {}, Tamanho: {}",
                nome, pageable.getPageNumber(), pageable.getPageSize());

        if (nome == null || nome.trim().isEmpty()) {
            log.warn("Nome vazio ou nulo recebido para busca de doenças");
            throw new BadRequestException("Nome é obrigatório para busca");
        }

        try {
            Page<Doencas> doencas = doencasRepository.findByNomeContainingIgnoreCase(nome, pageable);
            log.debug("Listagem de doenças por nome concluída. Nome: {}, Total: {}", nome, doencas.getTotalElements());
            return doencas.map(doencasMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar doenças por nome. Nome: {}, Pageable: {}", nome, pageable, e);
            throw new InternalServerErrorException("Erro ao listar doenças por nome", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar doenças por nome. Nome: {}, Pageable: {}", nome, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoencasResponse> listarPorCodigoCid(String codigoCid, Pageable pageable) {
        log.debug("Listando doenças por código CID: {}. Página: {}, Tamanho: {}",
                codigoCid, pageable.getPageNumber(), pageable.getPageSize());

        if (codigoCid == null || codigoCid.trim().isEmpty()) {
            log.warn("Código CID vazio ou nulo recebido para busca de doenças");
            throw new BadRequestException("Código CID é obrigatório para busca");
        }

        try {
            Page<Doencas> doencas = doencasRepository.findByCodigoCid(codigoCid, pageable);
            log.debug("Listagem de doenças por código CID concluída. Código CID: {}, Total: {}", codigoCid, doencas.getTotalElements());
            return doencas.map(doencasMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar doenças por código CID. Código CID: {}, Pageable: {}", codigoCid, pageable, e);
            throw new InternalServerErrorException("Erro ao listar doenças por código CID", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar doenças por código CID. Código CID: {}, Pageable: {}", codigoCid, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "doencas", key = "#id")
    public DoencasResponse atualizar(UUID id, DoencasRequest request) {
        log.debug("Atualizando doença. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de doença");
            throw new BadRequestException("ID da doença é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de doença. ID: {}", id);
            throw new BadRequestException("Dados da doença são obrigatórios");
        }

        try {
            validarDadosBasicos(request);

            Doencas doencaExistente = doencasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Doença não encontrada com ID: " + id));

            atualizarDadosDoenca(doencaExistente, request);

            Doencas doencaAtualizada = doencasRepository.save(doencaExistente);
            log.info("Doença atualizada com sucesso. ID: {}", doencaAtualizada.getId());

            return doencasMapper.toResponse(doencaAtualizada);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar doença não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar doença. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar doença. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar doença. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "doencas", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo doença. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de doença");
            throw new BadRequestException("ID da doença é obrigatório");
        }

        try {
            Doencas doenca = doencasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Doença não encontrada com ID: " + id));

            if (Boolean.FALSE.equals(doenca.getActive())) {
                log.warn("Tentativa de excluir doença já inativa. ID: {}", id);
                throw new BadRequestException("Doença já está inativa");
            }

            doenca.setActive(false);
            doencasRepository.save(doenca);
            log.info("Doença excluída (desativada) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir doença não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir doença. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir doença. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir doença. ID: {}", id, e);
            throw e;
        }
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
        // Atualiza embeddables usando mappers
        if (request.getClassificacao() != null) {
            if (doenca.getClassificacao() == null) {
                doenca.setClassificacao(classificacaoDoencaMapper.toEntity(request.getClassificacao()));
            } else {
                classificacaoDoencaMapper.updateFromRequest(request.getClassificacao(), doenca.getClassificacao());
            }
        }
        if (request.getSintomas() != null) {
            if (doenca.getSintomas() == null) {
                doenca.setSintomas(sintomasDoencaMapper.toEntity(request.getSintomas()));
            } else {
                sintomasDoencaMapper.updateFromRequest(request.getSintomas(), doenca.getSintomas());
            }
        }
        if (request.getTratamentoPadrao() != null) {
            if (doenca.getTratamentoPadrao() == null) {
                doenca.setTratamentoPadrao(tratamentoPadraoDoencaMapper.toEntity(request.getTratamentoPadrao()));
            } else {
                tratamentoPadraoDoencaMapper.updateFromRequest(request.getTratamentoPadrao(), doenca.getTratamentoPadrao());
            }
        }
        if (request.getEpidemiologia() != null) {
            if (doenca.getEpidemiologia() == null) {
                doenca.setEpidemiologia(epidemiologiaDoencaMapper.toEntity(request.getEpidemiologia()));
            } else {
                epidemiologiaDoencaMapper.updateFromRequest(request.getEpidemiologia(), doenca.getEpidemiologia());
            }
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
        if (request.getCidPrincipal() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipal())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipal()));
            doenca.setCidPrincipal(cidPrincipal);
        }
    }
}

