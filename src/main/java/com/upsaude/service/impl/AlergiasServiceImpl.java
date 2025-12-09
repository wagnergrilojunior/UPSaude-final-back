package com.upsaude.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.AlergiasRequest;
import com.upsaude.api.response.AlergiasResponse;
import com.upsaude.entity.Alergias;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.AlergiasMapper;
import com.upsaude.repository.AlergiasRepository;
import com.upsaude.service.AlergiasService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlergiasServiceImpl implements AlergiasService {

    private final AlergiasRepository alergiasRepository;
    private final AlergiasMapper alergiasMapper;

    @Override
    @Transactional
    public AlergiasResponse criar(AlergiasRequest request) {
        log.debug("Criando nova Alergia — payload: {}", request);
        if (request == null) {
            log.error("Request de criação de Alergia é nula");
            throw new BadRequestException("Dados da alergia são obrigatórios");
        }

        try {
            validarDuplicidade(null, request);

            Alergias alergia = alergiasMapper.fromRequest(request);
            alergia.setActive(true);
            Alergias salvo = alergiasRepository.save(alergia);
            log.info("Alergia criada com sucesso. ID: {}", salvo.getId());
            return alergiasMapper.toResponse(salvo);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar Alergia. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar Alergia — payload: {}, Exception: {}", request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AlergiasResponse obterPorId(UUID id) {
        log.debug("Buscando Alergia por ID: {}", id);
        if (id == null) {
            log.error("ID para busca de Alergia é nulo");
            throw new BadRequestException("ID da alergia é obrigatório");
        }

        try {
            Alergias alergia = alergiasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Alergia não encontrada com ID: " + id));
            return alergiasMapper.toResponse(alergia);
        } catch (NotFoundException nf) {
            log.warn("Alergia não encontrada — ID: {}", id);
            throw nf;
        } catch (Exception ex) {
            log.error("Erro inesperado ao buscar Alergia por ID: {}, Exception: {}", id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlergiasResponse> listar(Pageable pageable) {
        log.debug("Listando alergias — page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Alergias> page = alergiasRepository.findAll(pageable);
            return page.map(alergiasMapper::toResponse);
        } catch (Exception ex) {
            log.error("Erro ao listar alergias — pageable: {}, Exception: {}", pageable, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @Override
    @Transactional
    public AlergiasResponse atualizar(UUID id, AlergiasRequest request) {
        log.debug("Atualizando Alergia — ID: {}, payload: {}", id, request);
        if (id == null) {
            log.error("ID para atualização de Alergia é nulo");
            throw new BadRequestException("ID da alergia é obrigatório");
        }
        if (request == null) {
            log.error("Request de atualização de Alergia é nula — ID: {}", id);
            throw new BadRequestException("Dados da alergia são obrigatórios");
        }

        try {
            Alergias existente = alergiasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Alergia não encontrada com ID: " + id));

            validarDuplicidade(id, request);

            alergiasMapper.updateFromRequest(request, existente);

            Alergias atualizado = alergiasRepository.save(existente);
            log.info("Alergia atualizada com sucesso. ID: {}", atualizado.getId());
            return alergiasMapper.toResponse(atualizado);
        } catch (NotFoundException nf) {
            log.warn("Tentativa de atualizar Alergia não existente — ID: {}", id);
            throw nf;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar Alergia. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar Alergia — ID: {}, payload: {}, Exception: {}", id, request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo Alergia — ID: {}", id);
        if (id == null) {
            log.error("ID para exclusão de Alergia é nulo");
            throw new BadRequestException("ID da alergia é obrigatório");
        }

        try {
            Alergias existente = alergiasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Alergia não encontrada com ID: " + id));

            if (Boolean.FALSE.equals(existente.getActive())) {
                log.warn("Tentativa de excluir Alergia já inativa — ID: {}", id);
                throw new BadRequestException("Alergia já está inativa");
            }

            existente.setActive(false);
            alergiasRepository.save(existente);
            log.info("Alergia desativada com sucesso. ID: {}", id);
        } catch (NotFoundException nf) {
            log.warn("Tentativa de excluir Alergia não existente — ID: {}", id);
            throw nf;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir Alergia. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir Alergia — ID: {}, Exception: {}", id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    /**
     * Valida se já existe uma alergia com o mesmo nome ou código interno no banco de dados.
     * 
     * @param id ID da alergia sendo atualizada (null para criação)
     * @param request dados da alergia sendo cadastrada/atualizada
     * @throws BadRequestException se já existe uma alergia com o mesmo nome ou código interno
     */
    private void validarDuplicidade(UUID id, AlergiasRequest request) {
        if (request == null) {
            return;
        }

        // Valida duplicidade do nome
        if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
            boolean nomeDuplicado;
            if (id == null) {
                // Criação: verifica se existe qualquer registro com este nome
                nomeDuplicado = alergiasRepository.existsByNome(request.getNome().trim());
            } else {
                // Atualização: verifica se existe outro registro (diferente do atual) com este nome
                nomeDuplicado = alergiasRepository.existsByNomeAndIdNot(request.getNome().trim(), id);
            }

            if (nomeDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar alergia com nome duplicado. Nome: {}", request.getNome());
                throw new BadRequestException(
                    String.format("Já existe uma alergia cadastrada com o nome '%s' no banco de dados", request.getNome())
                );
            }
        }

        // Valida duplicidade do código interno (apenas se fornecido)
        if (request.getCodigoInterno() != null && !request.getCodigoInterno().trim().isEmpty()) {
            boolean codigoDuplicado;
            if (id == null) {
                // Criação: verifica se existe qualquer registro com este código interno
                codigoDuplicado = alergiasRepository.existsByCodigoInterno(request.getCodigoInterno().trim());
            } else {
                // Atualização: verifica se existe outro registro (diferente do atual) com este código interno
                codigoDuplicado = alergiasRepository.existsByCodigoInternoAndIdNot(request.getCodigoInterno().trim(), id);
            }

            if (codigoDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar alergia com código interno duplicado. Código: {}", request.getCodigoInterno());
                throw new BadRequestException(
                    String.format("Já existe uma alergia cadastrada com o código interno '%s' no banco de dados", request.getCodigoInterno())
                );
            }
        }
    }
}
