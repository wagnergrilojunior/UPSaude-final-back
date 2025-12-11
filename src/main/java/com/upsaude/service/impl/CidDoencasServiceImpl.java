package com.upsaude.service.impl;

import com.upsaude.api.request.CidDoencasRequest;
import com.upsaude.api.response.CidDoencasResponse;
import com.upsaude.entity.CidDoencas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.CidDoencasMapper;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.service.CidDoencasService;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class CidDoencasServiceImpl implements CidDoencasService {

    private final CidDoencasRepository cidDoencasRepository;
    private final CidDoencasMapper cidDoencasMapper;

    @Override
    @Transactional
    @CacheEvict(value = "ciddoencas", allEntries = true)
    public CidDoencasResponse criar(CidDoencasRequest request) {
        log.debug("Criando novo CID de doença. Request: {}", request);

        if (request == null) {
            log.warn("Tentativa de criar CID de doença com request nulo");
            throw new BadRequestException("Dados do CID de doença são obrigatórios");
        }

        try {

            if (request.getCodigo() != null && cidDoencasRepository.findByCodigo(request.getCodigo()).isPresent()) {
                log.warn("Tentativa de criar CID de doença com código duplicado: {}", request.getCodigo());
                throw new ConflictException("Já existe um CID de doença com o código: " + request.getCodigo());
            }

            CidDoencas cidDoencas = cidDoencasMapper.fromRequest(request);
            cidDoencas.setActive(true);

            CidDoencas cidDoencasSalvo = cidDoencasRepository.save(cidDoencas);
            log.info("CID de doença criado com sucesso. ID: {}", cidDoencasSalvo.getId());

            return cidDoencasMapper.toResponse(cidDoencasSalvo);
        } catch (BadRequestException | ConflictException e) {
            log.warn("Erro de validação ao criar CID de doença. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar CID de doença. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir CID de doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar CID de doença. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "ciddoencas", key = "#id")
    public CidDoencasResponse obterPorId(UUID id) {
        log.debug("Buscando CID de doença por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de CID de doença");
            throw new BadRequestException("ID do CID de doença é obrigatório");
        }

        try {
            CidDoencas cidDoencas = cidDoencasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("CID de doença não encontrado com ID: " + id));

            log.debug("CID de doença encontrado. ID: {}", id);
            return cidDoencasMapper.toResponse(cidDoencas);
        } catch (NotFoundException e) {
            log.warn("CID de doença não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar CID de doença. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar CID de doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar CID de doença. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CidDoencasResponse> listar(Pageable pageable) {
        log.debug("Listando CID de doenças paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<CidDoencas> cidDoencas = cidDoencasRepository.findAll(pageable);
            log.debug("Listagem de CID de doenças concluída. Total de elementos: {}", cidDoencas.getTotalElements());
            return cidDoencas.map(cidDoencasMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar CID de doenças. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar CID de doenças", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar CID de doenças. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "ciddoencas", key = "#id")
    public CidDoencasResponse atualizar(UUID id, CidDoencasRequest request) {
        log.debug("Atualizando CID de doença. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de CID de doença");
            throw new BadRequestException("ID do CID de doença é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de CID de doença. ID: {}", id);
            throw new BadRequestException("Dados do CID de doença são obrigatórios");
        }

        try {

            CidDoencas cidDoencasExistente = cidDoencasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("CID de doença não encontrado com ID: " + id));

            if (request.getCodigo() != null && !request.getCodigo().equals(cidDoencasExistente.getCodigo())) {
                if (cidDoencasRepository.existsByCodigoAndIdNot(request.getCodigo(), id)) {
                    log.warn("Tentativa de atualizar CID de doença com código duplicado: {}. ID: {}", request.getCodigo(), id);
                    throw new ConflictException("Já existe outro CID de doença com o código: " + request.getCodigo());
                }
            }

            cidDoencasMapper.updateFromRequest(request, cidDoencasExistente);

            CidDoencas cidDoencasAtualizado = cidDoencasRepository.save(cidDoencasExistente);
            log.info("CID de doença atualizado com sucesso. ID: {}", cidDoencasAtualizado.getId());

            return cidDoencasMapper.toResponse(cidDoencasAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar CID de doença não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException | ConflictException e) {
            log.warn("Erro de validação ao atualizar CID de doença. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar CID de doença. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar CID de doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar CID de doença. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "ciddoencas", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo CID de doença. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de CID de doença");
            throw new BadRequestException("ID do CID de doença é obrigatório");
        }

        try {
            CidDoencas cidDoencas = cidDoencasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("CID de doença não encontrado com ID: " + id));

            if (Boolean.FALSE.equals(cidDoencas.getActive())) {
                log.warn("Tentativa de excluir CID de doença já inativo. ID: {}", id);
                throw new BadRequestException("CID de doença já está inativo");
            }

            cidDoencas.setActive(false);
            cidDoencasRepository.save(cidDoencas);
            log.info("CID de doença excluído (desativado) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir CID de doença não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir CID de doença. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir CID de doença. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir CID de doença", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir CID de doença. ID: {}", id, e);
            throw e;
        }
    }

}
