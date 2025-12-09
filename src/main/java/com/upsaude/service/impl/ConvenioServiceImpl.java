package com.upsaude.service.impl;

import com.upsaude.api.request.ConvenioRequest;
import com.upsaude.api.response.ConvenioResponse;
import com.upsaude.entity.Convenio;
import com.upsaude.entity.Endereco;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ConvenioMapper;
import com.upsaude.repository.ConvenioRepository;
import com.upsaude.repository.EnderecoRepository;
import com.upsaude.service.ConvenioService;
import com.upsaude.service.EnderecoService;
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
 * Implementação do serviço de gerenciamento de Convenio.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConvenioServiceImpl implements ConvenioService {

    private final ConvenioRepository convenioRepository;
    private final ConvenioMapper convenioMapper;
    @SuppressWarnings("unused") // Será usado quando requests aceitarem objeto completo EnderecoRequest
    private final EnderecoService enderecoService;
    private final EnderecoRepository enderecoRepository;

    @Override
    @Transactional
    @CacheEvict(value = "convenio", allEntries = true)
    public ConvenioResponse criar(ConvenioRequest request) {
        log.debug("Criando novo convênio. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar convênio com request nulo");
            throw new BadRequestException("Dados do convênio são obrigatórios");
        }

        try {
            validarDadosBasicos(request);

            Convenio convenio = convenioMapper.fromRequest(request);
            convenio.setActive(true);

            // Processa endereço se fornecido (Fase 2.1)
            if (request.getEndereco() != null) {
                Endereco endereco = enderecoRepository.findById(request.getEndereco())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado com ID: " + request.getEndereco()));
                convenio.setEndereco(endereco);
            }

            Convenio convenioSalvo = convenioRepository.save(convenio);
            log.info("Convênio criado com sucesso. ID: {}", convenioSalvo.getId());

            return convenioMapper.toResponse(convenioSalvo);
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao criar convênio. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar convênio. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir convênio", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar convênio. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "convenio", key = "#id")
    public ConvenioResponse obterPorId(UUID id) {
        log.debug("Buscando convênio por ID: {} (cache miss)", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de convênio");
            throw new BadRequestException("ID do convênio é obrigatório");
        }

        try {
            Convenio convenio = convenioRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + id));

            log.debug("Convênio encontrado. ID: {}", id);
            return convenioMapper.toResponse(convenio);
        } catch (NotFoundException e) {
            log.warn("Convênio não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar convênio. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar convênio", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar convênio. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConvenioResponse> listar(Pageable pageable) {
        log.debug("Listando convênios paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Convenio> convenios = convenioRepository.findAll(pageable);
            log.debug("Listagem de convênios concluída. Total de elementos: {}", convenios.getTotalElements());
            return convenios.map(convenioMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar convênios. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar convênios", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar convênios. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "convenio", key = "#id")
    public ConvenioResponse atualizar(UUID id, ConvenioRequest request) {
        log.debug("Atualizando convênio. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de convênio");
            throw new BadRequestException("ID do convênio é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de convênio. ID: {}", id);
            throw new BadRequestException("Dados do convênio são obrigatórios");
        }

        try {
            validarDadosBasicos(request);

            Convenio convenioExistente = convenioRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + id));

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            convenioMapper.updateFromRequest(request, convenioExistente);

            // Processa endereço se fornecido (Fase 2.1 e 6.1)
            if (request.getEndereco() != null) {
                Endereco endereco = enderecoRepository.findById(request.getEndereco())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado com ID: " + request.getEndereco()));
                convenioExistente.setEndereco(endereco);
            } else {
                // Se endereço não foi fornecido no request, mantém o existente (não remove)
                // O mapper já preserva o endereço existente devido ao ignore
            }

            Convenio convenioAtualizado = convenioRepository.save(convenioExistente);
            log.info("Convênio atualizado com sucesso. ID: {}", convenioAtualizado.getId());

            return convenioMapper.toResponse(convenioAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar convênio não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar convênio. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar convênio. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar convênio", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar convênio. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "convenio", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo convênio. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de convênio");
            throw new BadRequestException("ID do convênio é obrigatório");
        }

        try {
            Convenio convenio = convenioRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + id));

            if (Boolean.FALSE.equals(convenio.getActive())) {
                log.warn("Tentativa de excluir convênio já inativo. ID: {}", id);
                throw new BadRequestException("Convênio já está inativo");
            }

            convenio.setActive(false);
            convenioRepository.save(convenio);
            log.info("Convênio excluído (desativado) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir convênio não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir convênio. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir convênio. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir convênio", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir convênio. ID: {}", id, e);
            throw e;
        }
    }

    private void validarDadosBasicos(ConvenioRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do convenio são obrigatórios");
        }
    }

    // Método removido - agora usa convenioMapper.updateFromRequest diretamente
    // O MapStruct já preserva campos de controle automaticamente
}
