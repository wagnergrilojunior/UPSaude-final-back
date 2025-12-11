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
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import org.springframework.dao.DataAccessException;
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

    private void sanitizarRequest(AlergiasRequest request) {
        if (request == null) {
            return;
        }

        if (request.getNome() != null) {
            String nomeSanitizado = sanitizarString(request.getNome());
            if (nomeSanitizado != null && !nomeSanitizado.trim().isEmpty()) {
                request.setNome(nomeSanitizado.trim());
            }
        }

        if (request.getNomeCientifico() != null) {
            String nomeCientificoSanitizado = sanitizarString(request.getNomeCientifico());
            if (nomeCientificoSanitizado != null && !nomeCientificoSanitizado.trim().isEmpty()) {
                request.setNomeCientifico(nomeCientificoSanitizado.trim());
            } else if (nomeCientificoSanitizado != null && nomeCientificoSanitizado.trim().isEmpty()) {
                request.setNomeCientifico(null);
            }
        }

        if (request.getCodigoInterno() != null) {
            String codigoSanitizado = sanitizarString(request.getCodigoInterno());
            if (codigoSanitizado != null && !codigoSanitizado.trim().isEmpty()) {
                request.setCodigoInterno(codigoSanitizado.trim());
            } else if (codigoSanitizado != null && codigoSanitizado.trim().isEmpty()) {
                request.setCodigoInterno(null);
            }
        }

        if (request.getDescricao() != null) {
            String descricaoSanitizada = sanitizarString(request.getDescricao());
            request.setDescricao(descricaoSanitizada);
        }

        if (request.getSubstanciasRelacionadas() != null) {
            String substanciasSanitizadas = sanitizarString(request.getSubstanciasRelacionadas());
            request.setSubstanciasRelacionadas(substanciasSanitizadas);
        }

        if (request.getObservacoes() != null) {
            String observacoesSanitizadas = sanitizarString(request.getObservacoes());
            request.setObservacoes(observacoesSanitizadas);
        }
    }

    @Override
    @Transactional
    public AlergiasResponse criar(AlergiasRequest request) {
        log.debug("Criando nova Alergia — payload: {}", request);
        if (request == null) {
            log.error("Request de criação de Alergia é nula");
            throw new BadRequestException("Dados da alergia são obrigatórios");
        }

        try {

            sanitizarRequest(request);

            if (request.getNome() == null || request.getNome().trim().isEmpty()) {
                throw new BadRequestException("Nome da alergia é obrigatório e não pode conter apenas caracteres de controle inválidos");
            }

            validarDuplicidade(null, request);

            Alergias alergia = alergiasMapper.fromRequest(request);
            alergia.setActive(true);
            Alergias salvo = alergiasRepository.save(alergia);
            log.info("Alergia criada com sucesso. ID: {}", salvo.getId());
            return alergiasMapper.toResponse(salvo);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar Alergia. Erro: {}", e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar Alergia. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
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
        } catch (NotFoundException e) {
            log.warn("Alergia não encontrada. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar Alergia. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao buscar Alergia", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar Alergia. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlergiasResponse> listar(Pageable pageable) {
        log.debug("Listando alergias — page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Alergias> page = alergiasRepository.findAll(pageable);
            log.debug("Listagem de alergias concluída. Total de elementos: {}", page.getTotalElements());
            return page.map(alergiasMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar Alergias. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao listar Alergias", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar Alergias. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
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

            sanitizarRequest(request);

            if (request.getNome() == null || request.getNome().trim().isEmpty()) {
                throw new BadRequestException("Nome da alergia é obrigatório e não pode conter apenas caracteres de controle inválidos");
            }

            validarDuplicidade(id, request);

            alergiasMapper.updateFromRequest(request, existente);

            Alergias atualizado = alergiasRepository.save(existente);
            log.info("Alergia atualizada com sucesso. ID: {}", atualizado.getId());
            return alergiasMapper.toResponse(atualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar Alergia não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar Alergia. ID: {}, Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar Alergia. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao atualizar Alergia", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar Alergia. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw e;
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
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir Alergia não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir Alergia. ID: {}, Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir Alergia. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir Alergia", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir Alergia. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    private String sanitizarString(String str) {
        if (str == null) {
            return null;
        }

        return str.replaceAll("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F]", "");
    }

    private void validarDuplicidade(UUID id, AlergiasRequest request) {
        if (request == null) {
            return;
        }

        if (request.getNome() != null && !request.getNome().trim().isEmpty()) {

            String nomeSanitizado = sanitizarString(request.getNome().trim());
            if (nomeSanitizado == null || nomeSanitizado.isEmpty()) {
                log.warn("Nome da alergia contém apenas caracteres de controle inválidos");
                throw new BadRequestException("Nome da alergia não pode conter apenas caracteres de controle inválidos");
            }

            boolean nomeDuplicado;
            if (id == null) {

                nomeDuplicado = alergiasRepository.existsByNome(nomeSanitizado);
            } else {

                nomeDuplicado = alergiasRepository.existsByNomeAndIdNot(nomeSanitizado, id);
            }

            if (nomeDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar alergia com nome duplicado. Nome: {}", request.getNome());
                throw new BadRequestException(
                    String.format("Já existe uma alergia cadastrada com o nome '%s' no banco de dados", request.getNome())
                );
            }
        }

        if (request.getCodigoInterno() != null && !request.getCodigoInterno().trim().isEmpty()) {

            String codigoSanitizado = sanitizarString(request.getCodigoInterno().trim());
            if (codigoSanitizado == null || codigoSanitizado.isEmpty()) {
                log.warn("Código interno da alergia contém apenas caracteres de controle inválidos");
                throw new BadRequestException("Código interno não pode conter apenas caracteres de controle inválidos");
            }

            boolean codigoDuplicado;
            if (id == null) {

                codigoDuplicado = alergiasRepository.existsByCodigoInterno(codigoSanitizado);
            } else {

                codigoDuplicado = alergiasRepository.existsByCodigoInternoAndIdNot(codigoSanitizado, id);
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
