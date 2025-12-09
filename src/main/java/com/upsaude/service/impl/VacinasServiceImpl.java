package com.upsaude.service.impl;

import com.upsaude.api.request.VacinasRequest;
import com.upsaude.api.response.VacinasResponse;
import com.upsaude.entity.Vacinas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.VacinasMapper;
import com.upsaude.repository.VacinasRepository;
import com.upsaude.service.VacinasService;
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
 * Implementação do serviço de gerenciamento de Vacinas.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VacinasServiceImpl implements VacinasService {

    private final VacinasRepository vacinasRepository;
    private final VacinasMapper vacinasMapper;

    @Override
    @Transactional
    @CacheEvict(value = "vacinas", allEntries = true)
    public VacinasResponse criar(VacinasRequest request) {
        log.debug("Criando nova vacina. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar vacina com request nulo");
            throw new BadRequestException("Dados da vacina são obrigatórios");
        }

        try {
            validarDadosBasicos(request);
            validarDuplicidade(null, request);

            Vacinas vacinas = vacinasMapper.fromRequest(request);
            vacinas.setActive(true);

            Vacinas vacinasSalvo = vacinasRepository.save(vacinas);
            log.info("Vacina criada com sucesso. ID: {}", vacinasSalvo.getId());

            return vacinasMapper.toResponse(vacinasSalvo);
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao criar vacina. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar vacina. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir vacina", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar vacina. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "vacinas", key = "#id")
    public VacinasResponse obterPorId(UUID id) {
        log.debug("Buscando vacina por ID: {} (cache miss)", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de vacina");
            throw new BadRequestException("ID da vacina é obrigatório");
        }

        try {
            Vacinas vacinas = vacinasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Vacina não encontrada com ID: " + id));

            log.debug("Vacina encontrada. ID: {}", id);
            return vacinasMapper.toResponse(vacinas);
        } catch (NotFoundException e) {
            log.warn("Vacina não encontrada. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar vacina. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar vacina", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar vacina. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VacinasResponse> listar(Pageable pageable) {
        log.debug("Listando vacinas paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Vacinas> vacinas = vacinasRepository.findAll(pageable);
            log.debug("Listagem de vacinas concluída. Total de elementos: {}", vacinas.getTotalElements());
            return vacinas.map(vacinasMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar vacinas. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar vacinas", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar vacinas. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "vacinas", key = "#id")
    public VacinasResponse atualizar(UUID id, VacinasRequest request) {
        log.debug("Atualizando vacina. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de vacina");
            throw new BadRequestException("ID da vacina é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de vacina. ID: {}", id);
            throw new BadRequestException("Dados da vacina são obrigatórios");
        }

        try {
            validarDadosBasicos(request);

            Vacinas vacinasExistente = vacinasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Vacina não encontrada com ID: " + id));

            validarDuplicidade(id, request);

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            vacinasMapper.updateFromRequest(request, vacinasExistente);

            Vacinas vacinasAtualizado = vacinasRepository.save(vacinasExistente);
            log.info("Vacina atualizada com sucesso. ID: {}", vacinasAtualizado.getId());

            return vacinasMapper.toResponse(vacinasAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar vacina não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar vacina. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar vacina. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar vacina", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar vacina. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "vacinas", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo vacina. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de vacina");
            throw new BadRequestException("ID da vacina é obrigatório");
        }

        try {
            Vacinas vacinas = vacinasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Vacina não encontrada com ID: " + id));

            if (Boolean.FALSE.equals(vacinas.getActive())) {
                log.warn("Tentativa de excluir vacina já inativa. ID: {}", id);
                throw new BadRequestException("Vacina já está inativa");
            }

            vacinas.setActive(false);
            vacinasRepository.save(vacinas);
            log.info("Vacina excluída (desativada) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir vacina não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir vacina. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir vacina. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir vacina", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir vacina. ID: {}", id, e);
            throw e;
        }
    }

    private void validarDadosBasicos(VacinasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do vacinas são obrigatórios");
        }
    }

    /**
     * Valida se já existe uma vacina com o mesmo nome ou código interno no banco de dados.
     * 
     * @param id ID da vacina sendo atualizada (null para criação)
     * @param request dados da vacina sendo cadastrada/atualizada
     * @throws BadRequestException se já existe uma vacina com o mesmo nome ou código interno
     */
    private void validarDuplicidade(UUID id, VacinasRequest request) {
        if (request == null) {
            return;
        }

        // Valida duplicidade do nome
        if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
            boolean nomeDuplicado;
            if (id == null) {
                // Criação: verifica se existe qualquer registro com este nome
                nomeDuplicado = vacinasRepository.existsByNome(request.getNome().trim());
            } else {
                // Atualização: verifica se existe outro registro (diferente do atual) com este nome
                nomeDuplicado = vacinasRepository.existsByNomeAndIdNot(request.getNome().trim(), id);
            }

            if (nomeDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar vacina com nome duplicado. Nome: {}", request.getNome());
                throw new BadRequestException(
                    String.format("Já existe uma vacina cadastrada com o nome '%s' no banco de dados", request.getNome())
                );
            }
        }

        // Valida duplicidade do código interno (apenas se fornecido)
        if (request.getCodigoInterno() != null && !request.getCodigoInterno().trim().isEmpty()) {
            boolean codigoDuplicado;
            if (id == null) {
                // Criação: verifica se existe qualquer registro com este código interno
                codigoDuplicado = vacinasRepository.existsByCodigoInterno(request.getCodigoInterno().trim());
            } else {
                // Atualização: verifica se existe outro registro (diferente do atual) com este código interno
                codigoDuplicado = vacinasRepository.existsByCodigoInternoAndIdNot(request.getCodigoInterno().trim(), id);
            }

            if (codigoDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar vacina com código interno duplicado. Código: {}", request.getCodigoInterno());
                throw new BadRequestException(
                    String.format("Já existe uma vacina cadastrada com o código interno '%s' no banco de dados", request.getCodigoInterno())
                );
            }
        }
    }

    // Método removido - agora usa vacinasMapper.updateFromRequest diretamente
    // O MapStruct já preserva campos de controle automaticamente
}
